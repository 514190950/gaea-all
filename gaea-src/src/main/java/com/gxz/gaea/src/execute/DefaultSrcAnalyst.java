package com.gxz.gaea.src.execute;

import cn.hutool.core.io.FileUtil;
import com.gxz.gaea.core.component.Filter;
import com.gxz.gaea.core.component.GaeaComponentSorter;
import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.core.execute.analyst.LineAnalyst;
import com.gxz.gaea.core.factory.ThreadPoolFactory;
import com.gxz.gaea.core.model.CsvData;
import com.gxz.gaea.src.config.SrcEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class DefaultSrcAnalyst<M extends CsvData> implements Analyst<File> {

    protected static ThreadPoolExecutor executorService = ThreadPoolFactory.createThreadPool();

    @Autowired
    private LineAnalyst<M> lineAnalyst;

    @Autowired(required = false)
    private List<Filter<M>> filters;

    @Autowired
    private SrcEnvironment srcEnvironment;

    /**
     * 当前csv输出中缓存的csv记录数
     */
    protected AtomicInteger csvCount = new AtomicInteger(0);

    /**
     * 此map存放 用fileName 信息做key  行内容作为value的map
     */
    protected Map<String, List<String>> csvMap = new ConcurrentHashMap<>();

    @Override
    public void analysis(File file) {
        List<String> lines = FileUtil.readLines(file, StandardCharsets.UTF_8);
        if (isSerial()) {
            analysisLines(lines);
        }
        System.out.println("解析了");


//            if (properties.getExecutor() <= 1 || properties.getExecutor() <= lines.size() * 10) {
//                // 单线程
//                analysisLine(lines);
//            } else {
//                // 多线程执行
//                List<List<String>> partitions = Lists.partition(lines, (lines.size() / properties.getExecutor()) + 1);
//                CountDownLatch countDownLatch = new CountDownLatch(partitions.size());
//                for (List<String> partition : partitions) {
//                    executorService.execute(() -> {
//                        try {
//                            analysisLine(partition);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            log.error(e.getMessage());
//                            log.error("解析实体时出现特殊异常");
//                        } finally {
//                            countDownLatch.countDown();
//                        }
//                    });
//                }
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
    }

    private boolean isSerial() {
        return false;
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
                        continue;
                    }
                }
            }

            // 添加
            // appendCsvData(pack);
        }
    }

    /**
     * 把实体添加到csv缓存中  如果缓存超过了一定量 就输出一次 释放缓存
     * 这里使用SynchronizedList 而不使用 CopyOnWriteArrayList
     * 因为CopyOnWrite原理是通过每一次添加都复制来保证在何时都可以遍历出完整list
     * 而Src业务并不需要遍历 只需要最后整理 使用SynchronizedList可以使效率大大提高
     */
    protected void appendCsvData(M data, String category) {
        String csvLine = data.toCsv();

        // TODO: 2021/5/21 如何拿到SRC文件？
//        String s = data.toCsv();
//        String fileName = SrcCsvFactory.getCsvFileName(data, category,
//                gaeaEnvironment.getNodeName());
//        String csvLine = SrcCsvFactory.toCsv(data);
//        Long captureTime = data.getCaptureTime();
//        this.csvMap.computeIfAbsent(fileName, (k) -> Collections.synchronizedList(new ArrayList<>())).add(csvLine);
//        if (csvCount.incrementAndGet() >= this.properties.getMaxLine()) {
//            // 如果数量超过了阈值 写文件
//            outputCsvData();
//        }
    }


    private void appendCsv(File file, Collection<String> lines) {
        File parentFile = file.getParentFile();
        if (parentFile.exists() || parentFile.mkdirs()) {
            FileUtil.appendUtf8Lines(lines, file);
        }
        log.error("文件{}无法创建", file);
    }




    @PostConstruct
    public void init() {
        if (!CollectionUtils.isEmpty(filters)) {
            filters.sort(GaeaComponentSorter.getInstance());
        }
    }

}
