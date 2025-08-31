package org.example.JavaCore.Thread;

import java.util.concurrent.Callable;

public class TaskTest implements Runnable {
    private final String name;
    private final int sleep;

    public TaskTest(String name, int sleep) {
        this.name = name;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        System.out.println("Task " + name + " started");
        try {
            Thread.sleep(sleep);
            System.out.println(Thread.currentThread().getName() );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task " + name + " finished");
    }
}
