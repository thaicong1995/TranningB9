package org.example.JavaCore.Creational_Pattern;

public class Singleton {
    private static Singleton instance;
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void showMessage(int a) {
        System.out.println(a);
    }
}
