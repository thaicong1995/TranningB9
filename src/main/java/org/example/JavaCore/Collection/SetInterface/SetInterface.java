package org.example.JavaCore.Collection.SetInterface;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class SetInterface {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        Set<String> set = new HashSet<String>();
//        Set<Integer> setint = new HashSet<Integer>();
        Set<String> set1 = new LinkedHashSet<String>();
        Set<String> set2 = new TreeSet<>();
//
//        // Tự động loại bỏ các giá trị trùng.
//        // HashMap
//        set.add("1");
//        set.add(new String("1"));
//        setint.add(1);
//        setint.add(1);
//        setint.add(null);
//        setint.add(null);
//        System.out.println("HashSet: " + set + " HashSet: " + setint);
//
//        // LinkedList + HashMap
//        set1.add("1");
//        set1.add(null);
//        set1.add(new String("1"));
//        set1.add(null);
//        System.out.println("HashSet: " + set1);
//
//        // Cây cân bằng - k nhận giá trị null
//        set2.add("1");
////        set2.add(null); // nullpointer
//        set2.add("1");
//        System.out.println("HashSet: " + set2);


        HashSet<Log> sett = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        sett.add(new Log("Log1", LocalDateTime.parse("2025-08-26 15:43:45.100", formatter)));
        sett.add(new Log("Log2", LocalDateTime.parse("2025-08-26 15:43:45.200", formatter)));
        sett.add(new Log("Log3", LocalDateTime.parse("2025-08-26 15:43:45.300", formatter)));
        sett.add(new Log("Log4", LocalDateTime.parse("2025-08-26 15:43:45.400", formatter)));
        sett.add(new Log("Log5", LocalDateTime.parse("2025-08-26 15:43:45.500", formatter)));


//        // size bucket =
//        for (int i = 1; i <= 13; i++) {
//            sett.add("User" + i);
//        }
//

//        sett.add("Alice");
//        sett.add("Bob1");
//        sett.add("Alic1e");
//        sett.add("Bob2");
//        sett.add("Alic2e");
//        sett.add("Bob3");
//        sett.add("Alic3e");
//        sett.add("Bob43");
//        sett.add("Alic322e");
//        sett.add("Bob4222");
//        sett.add("Alic3e22222");
//        sett.add("Bob4d");
//        sett.add("Alic3fe");
//        sett.add("Bob4d");
//        sett.add("Alicf3e");
//        sett.add("Bob4s");
//        sett.add("Bob4s");
//
//        sett.add("1");

        Stack<String> an = new Stack<>();
        // Lấy HashMap nội bộ của HashSet
        Field mapField = HashSet.class.getDeclaredField("map");
        mapField.setAccessible(true);
        HashMap<?, ?> map = (HashMap<?, ?>) mapField.get(sett);

        // Lấy mảng table (bucket array) trong HashMap
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);

        System.out.println("Bucket array length: " + table.length + "\n");

        // Duyệt từng bucket
        for (int i = 0; i < table.length; i++) {
            Object node = table[i];
            int length = 0;
            Object temp = node;
            while (temp != null) {
                length++;
                Field nextField = temp.getClass().getDeclaredField("next");
                nextField.setAccessible(true);
                temp = nextField.get(temp);
            }

            if (node != null) {
                System.out.print("Bucket " + i + " (length=" + length + "): ");
                while (node != null) {
                    Field keyField = node.getClass().getDeclaredField("key");
                    Field valueField = node.getClass().getDeclaredField("value");
                    Field nextField = node.getClass().getDeclaredField("next");

                    keyField.setAccessible(true);
                    valueField.setAccessible(true);
                    nextField.setAccessible(true);

                    Object key = keyField.get(node);
                    Object value = valueField.get(node);

                    // hashCode gốc của key
                    int rawHash = key.hashCode();

                    // HashMap xử lý hash (XOR high bits với low bits)
                    int storedHash = rawHash ^ (rawHash >>> 16);

                    // Chuyển sang nhị phân 32 bit để dễ quan sát
                    String rawHashBin = String.format("%32s", Integer.toBinaryString(rawHash)).replace(' ', '0');
                    String storedHashBin = String.format("%32s", Integer.toBinaryString(storedHash)).replace(' ', '0');

                    System.out.print("[key=" + key
                            + ", rawHash=" + rawHashBin + "-"+ rawHash + " (hashCode gốc)"
                            + ", storedHash=" + storedHashBin + "-"+ storedHash + " (sau XOR)"
                            + ", value=" + value + "] ");

                    node = nextField.get(node);
                }
                System.out.println();
            } else {
                System.out.println("Bucket " + i + " (length=0): empty");
            }

            String a = "day la ai, ai la day";
            String b = " \"day la ai, ai la day\" lllll";

            if (b.contains(a)){
                System.out.println(a);
            }
        }
    }

    public static class Log {
        private String message;
        private LocalDateTime time;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public Log(String message, LocalDateTime time) {
            this.message = message;
            this.time = time;
        }
    }
}
