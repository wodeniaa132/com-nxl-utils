package com.nxl.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.common.*;

@Slf4j
public class ThreadPoolDemoV1 {

    public void startRunnerV2() {
        int nThreads = 20;
        long limit = 20;
//        long contentCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM base_todo_case_schedule WHERE status = '0'", Long.class);
        long contentCount = 0L;
        log.info("initial content count succeed");
        if (contentCount == 0) {
            log.warn("没有需要处理的数据，任务结束");
//            taskEndCallBack(runnerCallBack);
            return;
        }
        log.info("总共有 {} 条数据需要处理，开始初始化线程池，nThreads: {}, limit: {}", contentCount, nThreads, limit);
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException ignored) {
        }
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(this.getClass().getName() + "-%d").build();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);

        // 每次处理前部{total}条数据
        long total = nThreads * limit, changeCount = contentCount;
        while (changeCount > 0) {
            List<Map<String, Object>> informationList = getQueryList(total);

            long finalTotal = Math.min(changeCount, total);
            AtomicInteger atomicStart = new AtomicInteger(0);
            CountDownLatch countDownLatch = new CountDownLatch(nThreads);
            for (int i = 0; i < nThreads; i++) {
                long finalCount = changeCount;
                executorService.submit(() -> {
                    if (atomicStart.get() < finalTotal) {
                        int currentStart = atomicStart.getAndAdd((int) limit);
                        int currentEnd = Math.min((currentStart + (int) limit), informationList.size());
                        long realStart = contentCount - finalCount + currentStart;
                        long realEnd = realStart + limit;

                        log.info("{} 开始处理从 {} 到 {} 的数据", Thread.currentThread().getName(), realStart, realEnd);
                        try {
//                            detailRunner(informationList.subList(currentStart, currentEnd), realStart, limit);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("{} 处理从 {} 到 {} 失败，e: {}", Thread.currentThread().getName(), realStart, realEnd, e.getMessage());
                        } finally {
                            countDownLatch.countDown();
                        }
                    } else {
                        countDownLatch.countDown();
                    }
                });
            }
            // 等待上一轮完成才开始下一轮的提取
            try {
                countDownLatch.await();
            } catch (Exception ignore) {
            }
            changeCount -= total;
        }

        executorService.shutdown();
        log.info("{}, 任务结束", Thread.currentThread().getName());
    }

    private List<Map<String, Object>> getQueryList(Object param){
        return new ArrayList<>();
    }
}
