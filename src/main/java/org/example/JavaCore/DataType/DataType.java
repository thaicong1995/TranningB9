package org.example.JavaCore.DataType;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class DataType {
    public static void main(String[] args) {
        int a = 123;
        Integer b = a; // boxing
        String d = "123";

        System.out.println(b + "- DataType -" + b.getClass() );
        int c = Integer.valueOf(a); // unboxing
        System.out.println(c);
//      System.out.println(c + "- DataType -" + c.getClass());// tham trị không gọi được getClass()

//        Integer d = null;
//        int e = d; // NullPointerException
//        System.out.println(e);


        // So sánh

        // Nguyên thủy
        int a1 = 1;
        int a2 = 1;
        if (a1 == a2) {
            System.out.println("a1 == a2 -" + AddressUtil.addressOf(a1) + " - " +AddressUtil.addressOf(a2));
        }else {
            System.out.println("a1 != a2 -" + AddressUtil.addressOf(a1) + " - " +AddressUtil.addressOf(a2));
        }

        // Object
        String a3 = "C";
        String a4 = "C";
        String a5 = new String("C");

        // So sánh có cùng tham chiếu đến 1 value ??
        if (a3 == a4) {
            System.out.println("a3 == a4 -" + AddressUtil.addressOf(a3) + " - " +AddressUtil.addressOf(a4));
        }else {
            System.out.println("a3 != a4 -" + AddressUtil.addressOf(a3) + " - " + AddressUtil.addressOf(a4) );
        }

        // So sánh value ??
        if (a3.equals(a5)) {
            System.out.println("a3 equals a5 -" + AddressUtil.addressOf(a3) + " - " + AddressUtil.addressOf(a5));
        }

        if (a3 == a5) {
            System.out.println("a3 == a5 -" + AddressUtil.addressOf(a3) + " - " + AddressUtil.addressOf(a5));
        }else  {
            System.out.println("a3 != a5 -" + AddressUtil.addressOf(a3) + " - " + AddressUtil.addressOf(a5));
        }

        Integer a6 = 1;

        // Unboxing - int
        if (a6 == a1) {
            System.out.println("a1 == a6 -" + AddressUtil.addressOf(a1) + " - " + AddressUtil.addressOf(a6));
        }
        // Boxing - Integer
        if (a6.equals(a1)) {
            System.out.println("a6 equals a1 -" + AddressUtil.addressOf(a6) + " - " + AddressUtil.addressOf(a1));
        }

    }


    static class AddressUtil {
        private static final Unsafe unsafe;

        static {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static long addressOf(Object o) {
            Object[] array = new Object[]{o};
            int baseOffset = unsafe.arrayBaseOffset(Object[].class);
            return unsafe.getLong(array, (long) baseOffset);
        }
    }
}
