


static class SafeDateFormat {
    // 定义ThreadLocal变量
    static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    static DateFormat get() {
        return tl.get();
    }
}

注意：在线程池中使用ThreadLocal 需要避免内存泄漏和线程安全的问题

ExecutorService es;
ThreadLocal tl;
es.execute(() -> {
    // ThreadLocal增加变量
    tl.set(obj);
    try {
        // 省略业务逻辑代码
    } finally {
        // 手动清理ThreadLocal
        tl.remove();
    }
});


