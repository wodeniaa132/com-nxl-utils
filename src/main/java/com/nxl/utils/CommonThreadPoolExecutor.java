package com.nxl.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


/**
* @Description:    线程池
* @Author:         nixl
* @CreateDate:     2020/11/13
*/
public class CommonThreadPoolExecutor {

   /**
    * 需要处理的总数据条数
    */
   public static long COUNT = 1233251L;
   /**
    * 实现分页，线程安全，避免数据重复处理。比synchronized效率相对要高
    */
   public static AtomicLong LINE = new AtomicLong(0);

   /**
    * 并发共用线程
    */
   public static final ThreadPoolExecutor FUTURE_THREAD_POOL;
   /**
    * 每个线程处理的数据
    */
   public static int HANDLE_COUNT = 100;
   /**
    * 设置线程数量
    */
   public static int HANDLE_THREAD = 50;
   /**
    * 固定线程数
    */
   public static ExecutorService EXECUTOR_SERVICE  = Executors.newFixedThreadPool(HANDLE_THREAD);

   static {
       //线程池维护线程的最少数量,核心线程数
       final int PARTNER_CORE_POOL_SIZE = 20;
       //线程池维护线程的最大数量
       final int PARTNER_MAX_INUMPOOL_SIZE = 50;
       //线程池维护线程所允许的空闲时间
       final long PARTNER_KEEP_ALIVE_TIME = 0;
       //线程池维护线程所允许的空闲时间的单位
       final TimeUnit PARTNER_UNIT = TimeUnit.SECONDS;
       //线程池所使用的缓冲队列,这里队列大小为 2
       final BlockingQueue<Runnable> PARTNER_WORK_QUEUE = new ArrayBlockingQueue<Runnable>(100);

       //线程池对拒绝任务的处理策略：AbortPolicy为抛出异常；CallerRunsPolicy为重试添加当前的任务，他会自动重复调用execute()方法；DiscardOldestPolicy为抛弃旧的任务，DiscardPolicy为抛弃当前的任务
       final ThreadPoolExecutor.CallerRunsPolicy PARTNER_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
       FUTURE_THREAD_POOL = new ThreadPoolExecutor(PARTNER_CORE_POOL_SIZE, PARTNER_MAX_INUMPOOL_SIZE, PARTNER_KEEP_ALIVE_TIME, PARTNER_UNIT, PARTNER_WORK_QUEUE, PARTNER_HANDLER);
   }

   public static long getLine(){
       return LINE.addAndGet(HANDLE_COUNT);
   }

}
