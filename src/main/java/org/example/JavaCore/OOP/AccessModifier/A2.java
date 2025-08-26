package org.example.JavaCore.OOP.AccessModifier;

public class A2 extends A {
    public void display() {
        display_default();
    }

    public void display1() {
        display_protected();
    }

    C c = new C();
    C.B b = c.new B();

}
