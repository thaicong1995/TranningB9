package org.example.JavaCore.Thread;

import java.util.ArrayList;
import java.util.List;

public class Thread_ {
    private static final Object lockUser = new Object();
    private static final Object lockOrder = new Object();

    public static void main(String[] args) {

        List<TaskTest> tasks = new ArrayList<>();
        tasks.add(new TaskTest("Task-A", 2000));
        tasks.add(new TaskTest("Task-B", 3000));

        TaskService service = new TaskService();
        service.executor(tasks);

        System.out.println(">>> Normal tasks finished!");

        Runnable task1 = () -> {
            synchronized (lockUser) {
                System.out.println(Thread.currentThread().getName() + " locked User");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                synchronized (lockOrder) {
                    System.out.println(Thread.currentThread().getName() + " locked Order");
                }
            }
        };

        Runnable task2 = () -> {
            synchronized (lockOrder) {
                System.out.println(Thread.currentThread().getName() + " locked Order");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                synchronized (lockUser) {
                    System.out.println(Thread.currentThread().getName() + " locked User");
                }
            }
        };

        Thread t1 = new Thread(task1, "Task-Update-UserFirst");
        Thread t2 = new Thread(task2, "Task-Update-OrderFirst");

        t1.start();
        t2.start();

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            service.checkDeadlock();
        }
    }
}
