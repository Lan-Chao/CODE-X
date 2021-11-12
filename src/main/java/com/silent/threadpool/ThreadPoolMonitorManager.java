package com.silent.threadpool;

import com.silent.config.ThreadPoolConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zhao
 * @date 2021/11/11 17:00
 */

@Component
public class ThreadPoolMonitorManager {

    @Resource
    ThreadPoolConfig threadPoolConfig;

    private final ConcurrentMap<String, ThreadPoolExecutorMonitor> threadPoolExecutorMonitorConcurrentMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        threadPoolConfig.getExecutors().forEach(threadPoolProperties -> {
            if(!threadPoolExecutorMonitorConcurrentMap.containsKey(threadPoolProperties.getPoolName())){
                ThreadPoolExecutorMonitor executorForMonitor = new ThreadPoolExecutorMonitor(
                        threadPoolProperties.getCorePoolSize(),
                        threadPoolProperties.getMaximumPoolSize(),
                        threadPoolProperties.getKeepAliveTime(),
                        threadPoolProperties.getUnit(),
                        new ArrayBlockingQueue<>(100),
                        threadPoolProperties.getPoolName());
                threadPoolExecutorMonitorConcurrentMap.put(threadPoolProperties.getPoolName(),executorForMonitor);
            }
        });
    }

    public ThreadPoolExecutorMonitor getThreadPoolExecutor(String poolName){
        ThreadPoolExecutorMonitor threadPoolExecutorForMonitor = threadPoolExecutorMonitorConcurrentMap.get(poolName);
        if(threadPoolExecutorForMonitor == null){
            throw new RuntimeException(poolName + " is not exist ");
        }
        return threadPoolExecutorForMonitor;
    }

    public ThreadPoolExecutorMonitor getDefaultThreadPoolExecutor(){
        return threadPoolExecutorMonitorConcurrentMap.get(threadPoolConfig.getExecutors().get(0).getPoolName());
    }

    ConcurrentMap<String, ThreadPoolExecutorMonitor> getThreadPoolExecutorForMonitorConcurrentMap(){
        return this.threadPoolExecutorMonitorConcurrentMap;
    }

}
