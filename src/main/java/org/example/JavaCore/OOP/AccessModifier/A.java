package org.example.JavaCore.OOP.AccessModifier;

public class A {
    private void display_private() {
        System.out.println("A");
    }

    protected void display_protected() {
        System.out.println("A2");
    }

    public void display_public() {
        System.out.println("A3");
    }

    void display_default() {
        System.out.println("A4");
    }
}
