package org.example.JavaCore.Stactic_Final;

public class C  {
    public final int age;
    private String name;

    public C(int a, String name) {
        this.age = a;
        this.name = name;
    }

    public int getA() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "C{" +
                "a=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public void displayHashCode(String a) {
        System.out.println( System.identityHashCode(a));
    }
}