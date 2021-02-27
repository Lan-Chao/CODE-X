package com.silent.base;

import sun.misc.Contended;

import java.lang.annotation.Retention;
import java.util.Arrays;

/**
 * @author zhao
 */
public class FalseSharing implements  Runnable{

    public static final int NUM_THREADS = 4;
    public static final long ITERATIONS = 100L * 1000L * 1000L;

    private final int arrayIndex;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static{
        Arrays.fill(longs, new VolatileLong());
    }

    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads){
            t.start();
        }

        for (Thread t : threads){
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i){
            longs[arrayIndex].value = i;
        }
    }

    /**
     * 测试失败
     * */
    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("运行耗时：" + (System.currentTimeMillis() - start));
    }

    @Contended
    public final static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;

    }

}
