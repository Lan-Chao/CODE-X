package com.silent.base;

import sun.tools.jconsole.Worker;

/**
 *
 * 线程是系统资源分配的最小单位，它被包含在进程之中，是进程中的实际运作单位。
 *
 * @author zhao
 */
public class ThreadDemo {

    private Object obj1 = new Object();
    private Object obj2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("run......");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();
        System.out.println("main");
    }

    /**
     * 线程状态
     * NEW, // 尚未启动状态
     * RUNNABLE, // 可运行状态，但它可能还在等待处理器资源分配
     * BLOCKED, // 阻塞状态
     * WAITING, // 等待状态，等待另一个线程执行完毕
     * TIMED_WAITING, // 定时等待状态
     * TERMINATED; // 终止状态，线程已执行完毕
     * */


    /**
     *
     * 结束后会释放子线程所持有的所有锁，一般任何进行加锁的代码块，都是为了保护数据的一致性，
     * 如果在调用thread.stop()后导致了该线程所持有的所有锁的突然释放(不可控制)，
     * 那么被保护数据就有可能呈现不一致性，其他线程在使用这些被破坏的数据时，有可能导致一些很奇怪的应用程序错误
     *
     * Thread.interrupt()：修改中断状态，以实现合理、安全的中断当前线程
     * Thread.yield()：暂停执行当前线程，让出cpu执行其它线程（但是可能会被忽略）
     *
     * Thread.wait() / Thread.wait(long)：使调用该方法的线程释放共享资源锁，
     * 然后从运行状态退出，进入等待队列，直到被再次唤醒 或 定时等待 N 毫秒，如果没有通知就超时返回
     *
     * Thread.join() / Thread.join(long)：等待该线程结束后，再继续
     * 其作用就是将调用join的线程优先执行，当前正在执行的线程阻塞，直到调用join方法的线程执行完毕或者被打断，主要用于线程之间的交互
     *
     * Thread.sleep(long)：使当前线程在指定的时间内暂停执行
     * 暂停过程中调用此线程对象的 interrupt() 会唤醒线程并抛出 InterruptedException，之后继续执行
     *
     * Thread.notify() / Thread.notifyAll()：唤醒正在等待状态的线程
     * 常见使用场景是一个线程A调用了对象B的wait()方法进入等待状态，而另一个线程C调用了对象B的notify()/notifyAll()方法，
     * 线程A收到通知后退出等待队列，进入可运行状态，进而执行后续操作。A 和 C 两个线程通过对象B来完成交互，
     * 而对象上的wait()方法和notify()/notifyAll()方法的关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。
     * notify() 随机唤醒等待队列中等待同一共享资源的线程，此线程回退出等待队列，进入可运行状态
     * notifyAll() 唤醒所有正在等待队列中等待同一共享资源的全部线程，全部退出等待队列，进入可运行状态，优先级最高的开始执行
     * */

    /**
     * 避免死锁：
     * 1：避免嵌套锁 这是死锁最常见的原因，如果您已经持有一个资源，请避免锁定另一个资源。
     * 如果只使用一个对象锁，则几乎不可能出现死锁情况，比如以下代码对上边的循环嵌套部分进行修改，则避免了死锁的情况
     * 2:避免无限期等待 如果两个线程使用 thread join 无限期互相等待也会造成死锁，我们可以设定等待的最大时间来避免这种情况。
     * */

    private Runnable getRun(){
        return  new Runnable(){
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println(name + " acquiring lock on " + obj1);
                synchronized (obj1) {
                    System.out.println(name + " acquired lock on " + obj1);
                }
                System.out.println(name + " released lock on " + obj1);
                System.out.println(name + " acquiring lock on " + obj2);
                synchronized (obj2) {
                    System.out.println(name + " acquired lock on " + obj2);
                }
                System.out.println(name + " released lock on " + obj2);
                System.out.println(name + " finished execution.");
            }
        };
    }


}
