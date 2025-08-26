package org.example.JavaCore.OOP.AccessModifier;

public class A1 {
    A a = new A();

    //Cùng package truy cập đươợc protected
    public void display() {
        a.display_protected();
    }

    public void display1() {
        a.display_default();
    }
}
