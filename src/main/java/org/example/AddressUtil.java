package org.example;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class AddressUtil {
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