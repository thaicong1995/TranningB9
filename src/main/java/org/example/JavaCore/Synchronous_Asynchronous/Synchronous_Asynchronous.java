package org.example.JavaCore.Synchronous_Asynchronous;

public class Synchronous_Asynchronous {
    static int count = 0;
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = ()->{
            for (int i = 0; i < 1_000_000; i++) {
                System.out.println(Thread.currentThread().getName());
                synchronized (lock) {
                    count++;
                }
            }
        };

        Runnable runnable1 = ()->{
            for (int i = 0; i < 1_000_000; i++) {
                System.out.println(Thread.currentThread().getName());
                synchronized (lock) {
                    count++;
                }

            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable1);
        Thread thread3 = new Thread(runnable);
        Thread thread4 = new Thread(runnable1);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();


        System.out.println("Count: "+ count + "-" + Thread.currentThread().getName());



    }
}
