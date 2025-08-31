package org.example.Assignment.Poll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    private final ExecutorService pool;

    public ThreadPoolConfig(int threadCount) {
        pool = Executors.newFixedThreadPool(threadCount);
    }

    public ExecutorService getPool() {
        return pool;
    }

    public void shutdown() throws InterruptedException {
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
    }
}
