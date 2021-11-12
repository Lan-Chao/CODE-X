package com.silent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhao
 * @date 2021/11/11 15:53
 */

public class ThreadPoolExecutorMonitor extends ThreadPoolExecutor {

    private static final RejectedExecutionHandler DEFAULT_HANDLER = new AbortPolicy();

    ThreadPoolExecutorMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ThreadFactoryBuilder().setNameFormat(poolName + "-pool-%d").build(), DEFAULT_HANDLER);
    }


    /** 最短执行时间 */
    private long minCostTime;
    /** 最长执行时间 */
    private long maxCostTime;
    /** 总的耗时 */
    private AtomicLong totalCostTime = new AtomicLong();

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimeThreadLocal.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long costTime = System.currentTimeMillis() - startTimeThreadLocal.get();
        startTimeThreadLocal.remove();
        maxCostTime = Math.max(maxCostTime, costTime);
        if(getCompletedTaskCount() == 0){
            minCostTime=costTime;
        }
        minCostTime = Math.min(minCostTime, costTime);
        totalCostTime.addAndGet(costTime);
    }

    long getMinCostTime() {
        return minCostTime;
    }

    long getMaxCostTime() {
        return maxCostTime;
    }

    /**
     * 平均耗时
     * @author zhao
     * @date 2021/11/12
     */
    long getAverageCostTime(){
        if(getCompletedTaskCount() == 0 || totalCostTime.get() == 0){
            return 0;
        }
        return totalCostTime.get() / getCompletedTaskCount();
    }

}
