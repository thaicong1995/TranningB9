package org.example.JavaCore.Collection.MapInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapInterface {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== HashMap Demo ===");
        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put(null, "NullKey"); // Key null
//        hashMap.put("B", "Banana");
//        hashMap.put("A", "Apple");
        System.out.println(hashMap); // Thứ tự không xác định

        System.out.println("\n=== LinkedHashMap Demo ===");
        Map<String, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put(null, "NullKey");
        linkedMap.put("B", "Banana");
        linkedMap.put("A", "Apple");
        System.out.println(linkedMap); // Giữ thứ tự chèn

        System.out.println("\n=== TreeMap Demo ===");
        Map<String, String> treeMap = new TreeMap<>();
//         treeMap.put(null, "NullKey"); //Null poiter
        treeMap.put("B", "Banana");
        treeMap.put("A", "Apple");
        treeMap.put("C", "Cherry");
        System.out.println(treeMap); // Tự sắp xếp theo key (A, B, C)

        System.out.println("\n=== ConcurrentHashMap Demo ===");
        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();
//         concurrentMap.put(null, "NullKey"); //Null poiter
        concurrentMap.put("B", "Banana");
        concurrentMap.put("A", "Apple");
        concurrentMap.put("C", "Cherry");
        System.out.println(concurrentMap); // Thứ tự không xác định, thread-safe


         final int count = 0;
        Runnable addTask = () -> {
            for (int i = 0; i < 10; i++) {
//                deque2.add(i); // sử dụng synchron để đồng bộ các luồng
                synchronized(linkedMap) {
                    System.out.println("Thread add : " + Thread.currentThread().getName());
                    linkedMap.remove("c"+i, "C");
                }


//                System.out.println("Thread add : " + Thread.currentThread().getName());
//                linkedMap.put("c"+i, "C");
            }
        };

        Runnable removeTask = () -> {
            for (int i = 0; i < 10; i++) {
//                deque2.poll(); // Lấy và xóa phần tử đầu, trả về null nếu rỗng
                synchronized(linkedMap) {
                    System.out.println("Thread add : " + Thread.currentThread().getName());
                    linkedMap.remove("c"+i, "C");
                }
//                System.out.println("Thread add : " + Thread.currentThread().getName());
//                linkedMap.remove("c"+i);
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

        System.out.println("size: " + linkedMap.size());






    }
}
