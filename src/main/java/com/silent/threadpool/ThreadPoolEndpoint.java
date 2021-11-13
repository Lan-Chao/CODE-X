package com.silent.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @date 2021/11/11 15:51
 */

@Configuration
@Endpoint(id="thread-pool")
public class ThreadPoolEndpoint {

    @Resource
    private ThreadPoolMonitorManager threadPoolMonitorManager;

    @ReadOperation
    public Map<String, Object> threadPoolsMetric(){
        Map<String, Object> metricMap = new HashMap<>(16);
        List<Map> threadPools = new ArrayList<>();
        threadPoolMonitorManager.getThreadPoolExecutorForMonitorConcurrentMap().forEach((k, v)-> {
            Map<String,Object> poolInfo = new HashMap<>(16);
            poolInfo.put("thread.pool.name", k);
            poolInfo.put("thread.pool.core.size", v.getCorePoolSize());
            poolInfo.put("thread.pool.largest.size", v.getLargestPoolSize());
            poolInfo.put("thread.pool.max.size", v.getMaximumPoolSize());
            poolInfo.put("thread.pool.thread.count", v.getPoolSize());
            poolInfo.put("thread.pool.max.costTime", v.getMaxCostTime());
            poolInfo.put("thread.pool.average.costTime", v.getAverageCostTime());
            poolInfo.put("thread.pool.min.costTime", v.getMinCostTime());
            poolInfo.put("thread.pool.active.count", v.getActiveCount());
            poolInfo.put("thread.pool.completed.taskCount", v.getCompletedTaskCount());
            poolInfo.put("thread.pool.queue.name", v.getQueue().getClass().getName());
            poolInfo.put("thread.pool.queue.size", v.getQueue().size());
            poolInfo.put("thread.pool.queue.remainingCapacity", v.getQueue().remainingCapacity());
            poolInfo.put("thread.pool.rejected.name", v.getRejectedExecutionHandler().getClass().getName());
            poolInfo.put("thread.pool.task.count", v.getTaskCount());
            threadPools.add(poolInfo);
        });
        metricMap.put("threadPools", threadPools);
        return metricMap;
    }

}
