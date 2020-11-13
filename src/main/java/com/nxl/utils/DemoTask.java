package com.nxl.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : nixl
 * @date : 2020/11/13
 */
@Slf4j
public class DemoTask implements Runnable {

//    private EndProcess endProcess;
//
//    public EndProcessTask(EndProcess endProcess){
//        this.endProcess = endProcess;
//    }


          //调用demo
//        int handleThread = CommonThreadPoolExecutor.HANDLE_THREAD;
//        ExecutorService executorService = CommonThreadPoolExecutor.EXECUTOR_SERVICE;
//        for(int i=0 ; i< handleThread ;i++){
//            EndProcessTask endProcessTask = new EndProcessTask(endProcess);
//            executorService.execute(endProcessTask);
//        }

    @Override
    public void run(){
        while (true) {
            long count = CommonThreadPoolExecutor.COUNT;
            int handleCount = CommonThreadPoolExecutor.HANDLE_COUNT;
            long num = CommonThreadPoolExecutor.getLine();
            Long start = num - handleCount;
            if ( start < count) {
                if (num >= count) {
                    log.info("finish handle< {}, {}>pieces of data", start, count);
                    try {
                        Long end = count - start;
//                        endProcess.execByType(start.intValue());
                    } catch (Exception e) {
                        log.error("序号为：< "+start+" >的记录无法执行convertAndSaveByOffset: " + e);
                    }
                } else {
                    log.info(Thread.currentThread().getName()+" start handle<" + start + ", " + num+">pieces of data");
                    try {
//                        endProcess.execByType(start.intValue());
                    } catch (Exception e) {
                        log.error("序号为：< "+start+" >的记录无法执行convertAndSaveByOffset: " + e);
                    }
                }
            } else {
                log.info("convertAndSaveByOffset finished........");
                break;
            }
        }
    }

}