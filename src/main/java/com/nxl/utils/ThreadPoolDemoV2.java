package com.nxl.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ThreadPoolDemoV2 {

    public void startRunner() {
        log.info("----------------------mission start-----------------------");
//        Map<String, String> map = checkUpdate();
//        log.info(map.toString());
//        if (map.size() == 0) {
//            return;
//        }
//        String values = getValues(map);
//        long contentCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM base_todo_case_schedule WHERE `status` NOT IN ('2', '3') AND `table_name` IN (" + values + ")", Long.class);
        long contentCount = 0L;
        if (contentCount == 0) {
            return;
        }
        long limit = 20;
        int nThreads = 20;
        log.info("总共有 {} 条数据需要处理，开始初始化线程池，nThreads: {}, limit: {}", contentCount, nThreads, limit);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException ignored) {
        }

        AtomicLong atomicStart = new AtomicLong(0);
        ThreadFactory docProtestFactory = new ThreadFactoryBuilder().setNameFormat("Runner-%d").build();

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), docProtestFactory);
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(() -> {
                while (atomicStart.get() < contentCount) {
                    long currentStart = atomicStart.getAndAdd(limit);
                    long currentEnd = currentStart + limit;
                    log.info("{} extract start from {} to {}", Thread.currentThread().getName(), currentStart, currentEnd);
//                    detailRunner(map, currentStart, limit);
//                    log.info("detailRunner");
                }
                log.info("{}, extract end", Thread.currentThread().getName());
            });
        }
    }
}
