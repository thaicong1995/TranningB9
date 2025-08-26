package org.example.JavaCore.Stringg;

public class Stringg {
    public static void main(String[] args) {
        // new tạo mới 1 obj ngoài String pool.
        // intern() kiểm tra nếu String pool đã có value này thì trả về tham chiếu trong pool,
        // nếu chưa có thì thêm vào pool và trả về tham chiếu trong pool
        char[] data = {'H', 'e', 'l', 'l', 'o'};
        //Tạo chuỗi từ mảng char
        String str = new String(data).intern();

        String str0 = "Hello";
        String str1 = new String(str0) ;
        String str2 = "Hello0000";

        System.out.println("Str0 : " + str0.length() + " - " + System.identityHashCode(str0) + " - " + "Str1 : " + str1.length() + " - " + System.identityHashCode(str1) );
        System.out.println(str1 == str0); // true

        // Tạo chuỗi con từ mảng char với tham số (mảng, vị trí bắt đầu, độ dài)
        String str3 = new String(data, 1,3);

        System.out.println("Str3 : " +str3);
        // So sánh độ dài 2 chuỗi return int length str2 - str1
        System.out.println( str2.compareTo(str1));

        System.out.println(str + " - " + str.getClass() + " - " + System.identityHashCode(str));
        System.out.println(str0 + " - " + str0.getClass() + " - " + System.identityHashCode(str0));
        System.out.println(str1 + " - " + str1.getClass() + " - " + System.identityHashCode(str1));
        // Bất biến tạo 1 obj moi trong heap
        str = str + " World";
        System.out.println(str + " - " + str.getClass() + " - " + System.identityHashCode(str));

        str = null;
        System.out.println("Before GC");
        System.gc();
        System.out.println(str + " - " + " - " + System.identityHashCode(str));

        //2 Cách chính tạo string

        // Tao trong String pool nên dùng == để so sánh.(so sánh reference trong stack tới pool)
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = s1;
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);


        // Tao trong Heap dùng equals để so sánh. (so sánh từng char trong value)
        String s4 = new String("Hello");
        System.out.println(s1 == s4); // khác tham chiếu => false
        System.out.println(s1.equals(s4)); // giống value => true


        // So sánh

        String s5 = new String("HELLO");
        // so sánh tham chiếu
        System.out.println(s1 == s5);
        // so sánh value
        System.out.println(s1.equals(s5));

        // so sánh unicode từng ký tự trong chuỗi s1 va s5 - trả về int - <0, =0, >0.
        // Thuongf dùng để sắp xếp
        System.out.println(s1.compareTo(s5));
        // so sánh value không phân biệt hoa thường
        System.out.println(s1.compareToIgnoreCase(s1));

    }
}
