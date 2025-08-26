package org.example.JavaCore.OOP.This_Super;

public class Car {
    private String color;

    public Car(String color) {
        this.color = color;
        System.out.println("Inside constructor, this = " + this.hashCode());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void printThis() {
        System.out.println("In printThis, this = " + this.hashCode());
    }

    @Override
    public String toString() {
        return "Car{" + "objectHash=" + this.hashCode() + ", color=" + color + '}';
    }
}
