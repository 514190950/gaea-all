package com.gxz.gaea.src.execute;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.gxz.gaea.core.component.ConcurrentFileAppender;
import com.gxz.gaea.core.component.FileAppender;
import com.gxz.gaea.core.component.Filter;
import com.gxz.gaea.core.component.GaeaComponentSorter;
import com.gxz.gaea.core.component.SimpleFileAppender;
import com.gxz.gaea.core.config.GaeaEnvironment;
import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.core.execute.analyst.LineAnalyst;
import com.gxz.gaea.core.factory.ThreadPoolFactory;
import com.gxz.gaea.core.util.DateUtils;
import com.gxz.gaea.src.config.SrcEnvironment;
import com.gxz.gaea.src.model.SrcData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class DefaultSrcAnalyst<M extends SrcData> implements Analyst<File> {

    protected static ThreadPoolExecutor executorService = ThreadPoolFactory.createThreadPool();

    @Autowired
    private LineAnalyst<M> lineAnalyst;

    @Autowired(required = false)
    private List<Filter<M>> filters;

    @Autowired
    private SrcEnvironment srcEnvironment;

    @Autowired
    private GaeaEnvironment gaeaEnvironment;

    /**
     * 此map存放 用fileName 信息做key  行内容作为value的map
     * Map<FileName,Lines>
     */
    protected Map<String, FileAppender> csvMap;

    private boolean isSerial = false;

    @Override
    public void analysis(File file) {
        List<String> lines = FileUtil.readLines(file, StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            log.warn("{},没有内容，跳过本次执行", file.getAbsolutePath());
            return;
        }
        if ((isSerial = isSerial(lines))) {
            serialAnalysisLines(file, lines);
        } else {
            parallelAnalysisLines(file, lines);
        }
    }


    private void parallelAnalysisLines(File file, List<String> lines) {
        csvMap = new ConcurrentHashMap<>();
        if (log.isTraceEnabled()) {
            log.trace("[{}],有{}行数据,将多线程处理", file.getAbsolutePath(), lines.size());
        }
        List<List<String>> partitions = Lists.partition(lines, (lines.size() / srcEnvironment.getExecutor()) + 1);
        CountDownLatch countDownLatch = new CountDownLatch(partitions.size());
        for (List<String> partition : partitions) {
            executorService.submit(()->{
                try{
                    analysisLines(partition);
                }catch (Exception e){
                    log.error(e.getMessage());
                    log.error("解析实体时出现特殊异常");
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void serialAnalysisLines(File file, List<String> lines) {
        csvMap = new HashMap<>();
        if (log.isTraceEnabled()) {
            log.trace("[{}],有{}行数据,将单线程处理", file.getAbsolutePath(), lines.size());
        }
        analysisLines(lines);
    }

    private boolean isSerial(List<String> lines) {
        // 当行数小于阈值 或者配置的线程数小于等于1 使用单线程
        return lines.size() <= srcEnvironment.getSerialMax() || srcEnvironment.getExecutor() <= 1;
    }

    private void analysisLines(List<String> lines) {
        for (String line : lines) {
            M pack;
            try {
                pack = lineAnalyst.pack(line);
            } catch (Exception e) {
                log.error("错误信息:{},错误SRC：{}", e.getMessage(), line);
                continue;
            }
            if (!CollectionUtils.isEmpty(filters)) {
                for (Filter<M> filter : filters) {
                    if (!filter.filter(pack)) {
                        //todo 这里可以加配置 当过滤器存在问题的时候可以使用策略
                        break;
                    }
                }
            }
            // 添加
            appendCsvData(pack);
        }
    }

    /**
     * 把实体添加到csv缓存中  如果缓存超过了一定量 就输出一次 释放缓存
     * 如果单线程执行  使用{@link SimpleFileAppender}类
     * 如果多线程执行  使用{@link ConcurrentFileAppender}类
     */
    protected void appendCsvData(M data) {
        String csvLine = data.toCsv();
        String csvFilePath = getCsvFilePath(data);
        Objects.requireNonNull(this.csvMap.computeIfAbsent(csvFilePath,
                (k) -> {
                    final int outPutMaxLine = srcEnvironment.getOutPutMaxLine();
                    try (FileAppender fa = isSerial ?
                            new SimpleFileAppender(outPutMaxLine, () -> new File(csvFilePath)) :
                            new ConcurrentFileAppender(outPutMaxLine, () -> new File(csvFilePath))) {
                        return fa;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })).add(csvLine);
    }


    /**
     * 根据数据的捕获时间返回数据路径
     *
     * @param srcData src数据实体
     * @return 文件名称 ${捕获时间 yyyyMMdd} / ${捕获时间 yyyyMMddHHmm}.${节点名称}.csv
     **/
    private String getCsvFilePath(SrcData srcData) {
        String nodeName = gaeaEnvironment.getNodeName();
        Long captureTime = srcData.getCaptureTime();
        String dirName = DateUtils.format(captureTime, "yyyyMMdd");
        String fileName = srcEnvironment.getProtocol() + "_"
                + DateUtils.format(captureTime, "yyyyMMddHHmm") + "." + nodeName + ".csv";
        return File.separator + dirName + File.separator + fileName;
    }


    /**
     * 初始化的时候给过滤器排序
     **/
    @PostConstruct
    public void init() {
        if (!CollectionUtils.isEmpty(filters)) {
            filters.sort(GaeaComponentSorter.getInstance());
        }
    }

    @Override
    public void free(File file) {
        this.csvMap = null;
    }
}
