package org.example.JavaCore.Creational_Pattern;

public class Main_ {
    public static void main(String[] args) {
        //singleton - s1 -s2 cùng 1 instance
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        Singleton s3 = new Singleton();
        // s1-s2 cùng 1 instance - s3 khác instance
        System.out.println(s1 +"-" +
                            "--"+ s2 + "---"
                            + s3);

        // factory thông qua lơp Interface.
        Interface i = new Impl();
        i.abc();
    }
}
