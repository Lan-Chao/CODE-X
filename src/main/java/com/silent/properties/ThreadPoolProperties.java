package com.silent.properties;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author zhao
 * @date 2021/11/11 17:01
 */
@Data
public class ThreadPoolProperties {

    private String poolName;
    private int corePoolSize;
    private int maximumPoolSize = Runtime.getRuntime().availableProcessors();
    private long keepAliveTime = 60;
    private TimeUnit unit = TimeUnit.SECONDS;
    private int queueCapacity = Integer.MAX_VALUE;

}
