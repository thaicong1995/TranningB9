package org.example.JavaCore.OOP.This_Super;

public class Child extends Parent {
    public void checkReference() {
        System.out.println("Child: " + this.hashCode());
        // gọi method của cha, bên trong cha dùng this.hashCode()
        super.printHashCode();
    }
}