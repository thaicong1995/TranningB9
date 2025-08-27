package org.example.JavaCore.QueueInterface;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueInterface {
    public static void main(String[] args) throws InterruptedException {
       Queue<Integer> queue = new ArrayDeque<>(); //Có thể dùng như Queue hoặc Deque, nhanh hơn LinkedList khi thao tác đầu/cuối.
        // Thích hợp hàng đợi đơn giản trong bộ nhớ liên tục.
       Queue<Integer> queue1 = new PriorityQueue<>(); //Hàng đợi theo thứ tự ưu tiên (không theo FIFO).
        // Thường dùng trong thuật toán tìm đường ngắn nhất (Dijkstra), hệ thống task có độ ưu tiên.
       Queue<Integer> queue2 = new LinkedList<>(); //Có thể làm Queue hoặc Deque. Thêm/xóa nhanh ở đầu/cuối.
        // Thường dùng khi cần hàng đợi linh hoạt, kích thước thay đổi liên tục.


       Deque<Integer> deque = new LinkedList<>(); // dùng cho cả queue và dequeu khi thêm và xóa ở đầu cuối.
       Deque<Integer> deque1 = new ArrayDeque<>(); // Dùng khi muốn stack hoặc queue tốc độ cao, thao tác ở đầu/cuối nhanh hơn LinkedList.
       Deque<Integer> deque2 = new ConcurrentLinkedDeque<>(); //Dùng trong môi trường đa luồng, thao tác không khóa.

        deque2.add(1);
        deque2.add(2);
        deque2.add(3);

        Runnable addTask = () -> {
            for (int i = 0; i < 1000; i++) {
                queue1.add(i); // sử dụng synchron để đồng bộ các luồng
//                synchronized(deque2) {
//                    System.out.println("Thread add : " + Thread.currentThread().getName());
//                    deque2.add(i);
//                }
            }
        };

        Runnable removeTask = () -> {
            for (int i = 0; i < 1000; i++) {
                queue1.poll(); // Lấy và xóa phần tử đầu, trả về null nếu rỗng
//                synchronized(deque2) {
//                    System.out.println("Thread poll : " + Thread.currentThread().getName());
//                    deque2.poll();
//                }
            }
        };

        Thread t1 = new Thread(addTask);
        Thread t2 = new Thread(removeTask);
        Thread t3 = new Thread(addTask);
        Thread t4 = new Thread(removeTask);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Kích thước cuối: " + deque1.size());



    }
}
