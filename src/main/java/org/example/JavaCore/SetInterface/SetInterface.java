package org.example.JavaCore.SetInterface;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetInterface {
    public static void main(String[] args) {
        Set<String> set = new HashSet<String>();
        Set<Integer> setint = new HashSet<Integer>();
        Set<String> set1 = new LinkedHashSet<String>();
        Set<String> set2 = new TreeSet<>();

        // Tự động loại bỏ các giá trị trùng.
        // HashMap
        set.add("1");
        set.add(new String("1"));
        setint.add(1);
        setint.add(1);
        setint.add(null);
        setint.add(null);
        System.out.println("HashSet: " + set + " HashSet: " + setint);

        // LinkedList + HashMap
        set1.add("1");
        set1.add(null);
        set1.add(new String("1"));
        set1.add(null);
        System.out.println("HashSet: " + set1);

        // Cây cân bằng - k nhận giá trị null
        set2.add("1");
//        set2.add(null); // nullpointer
        set2.add("1");
        System.out.println("HashSet: " + set2);
    }
}
