package com.gxz.gaea.src.execute;

import cn.hutool.core.io.FileUtil;
import com.gxz.gaea.core.component.Filter;
import com.gxz.gaea.core.component.GaeaComponentSorter;
import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.core.execute.analyst.LineAnalyst;
import com.gxz.gaea.core.factory.ThreadPoolFactory;
import com.gxz.gaea.core.model.CsvData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
        if(isSerial()){
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

//    /**
//     * 把实体添加到csv缓存中  如果缓存超过了一定量 就输出一次 释放缓存
//     */
//    protected void appendCsvData(M data, String category) {
//        String fileName = SrcCsvFactory.getCsvFileName(data, category,
//                gaeaEnvironment.getNodeName());
//        String csvLine = SrcCsvFactory.toCsv(data);
//        Long captureTime = data.getCaptureTime();
//        this.csvMap.computeIfAbsent(fileName, (k) -> new CopyOnWriteArrayList<>()).add(csvLine);
//        if (minTime.get() == 0L || captureTime < minTime.get()) {
//            minTime.set(captureTime);
//        }
//        if (captureTime > maxTime.get()) {
//            maxTime.set(captureTime);
//        }
//        if (csvCount.incrementAndGet() >= this.properties.getMaxLine()) {
//            // 如果数量超过了阈值 写文件
//            outputCsvData();
//        }
//    }




    public static void main(String[] args) throws IOException {
        // 所有文件都在这个root下面建
        String root = "/Users/gongxuanzhang/教程/雷丰阳设计模式加源码解析/test/";
        String[] dirNames = new String[1000];
        // 1000个上级文件夹名
        for (int i = 0; i < dirNames.length; i++) {
            dirNames[i] = root + "父文件夹"+i;
        }
        // 30000个文件平均落在10000个文件夹下面
        File[] allFile = new File[30000];
        for (int i = 0; i < 30000; i++) {
            allFile[i] = new File(dirNames[i % 1000] + "/" + "文件" + i + "txt");
        }
        long st = System.currentTimeMillis();
       // String policy = firstDir(dirNames,allFile);
         String policy = allFile(allFile);
        System.out.println(policy);
        System.out.println(System.currentTimeMillis() - st);

    }
    public static String firstDir(String[] dirNames,File[] allFile){
        for (int i = 0; i < dirNames.length; i++) {
            new File(dirNames[i]).mkdirs();
        }
        for (File file : allFile) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "先建文件夹 然后把所有的文件都建了";
    }

    public static String allFile( File[] allFile){
        for (File file : allFile) {
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "不管文件夹 直接把所有的都建了";
    }





    @PostConstruct
    public void init() {
        if (!CollectionUtils.isEmpty(filters)) {
            filters.sort(GaeaComponentSorter.getInstance());
        }
    }

}
