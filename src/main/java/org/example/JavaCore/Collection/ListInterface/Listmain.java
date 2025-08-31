package org.example.JavaCore.Collection.ListInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listmain {
    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("a");
        list.addFirst("b");

//        for (var item : list) {
//            if(item.equals("a")){
//                list.remove(item); //ConcurrentModificationException lỗi
//            }
//        }
//
         List<String> list2 = new ArrayList(list);


        Iterator<String> iterator = list.iterator();
        // Duyệt với forEachRemaining

        while (iterator.hasNext()) {
            if(iterator.next().equals("a")){
                iterator.remove();
            }
        }
        iterator.forEachRemaining(System.out::println);

    }
}
