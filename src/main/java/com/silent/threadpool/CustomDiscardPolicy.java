package com.silent.threadpool;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhao
 * @date 2021/11/16 15:07
 */
public class CustomDiscardPolicy extends ThreadPoolExecutor.DiscardPolicy {


    /**
     * 需要捕获 CancellationException 去处理
     * @author zhao
     * @date 2021/11/17
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            if (r instanceof FutureTask) {
                ((FutureTask) r).cancel(true);
            }
        }
    }
}
