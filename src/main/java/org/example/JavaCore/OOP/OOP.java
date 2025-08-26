package org.example.JavaCore.OOP;

import org.example.JavaCore.OOP.Abtract_Interface.Classb;
import org.example.JavaCore.OOP.Abtract_Interface.IClassb;
import org.example.JavaCore.OOP.AccessModifier.A;
import org.example.JavaCore.OOP.AccessModifier.A1;
import org.example.JavaCore.OOP.AccessModifier.A2;
import org.example.JavaCore.OOP.AccessModifier.C;
import org.example.JavaCore.OOP.This_Super.Car;
import org.example.JavaCore.OOP.This_Super.Child;

public class OOP {

    public static void main(String[] args) {
        A a = new A();
        A1 a1 = new A1();
        A2 a2 = new A2();
        B b = new B();
        D d = new D();
        C c = new C();
        a1.display();
        a1.display1();
        b.display_public();
        b.display1();
        b.display();
        d.display_default();

        Classb cb = new Classb();
//        cb.g();
//        cb.k();
//        cb.a();

        System.out.println(cb.ccc());

        IClassb icb = new Classb();

        icb.a();
        icb.b();


        Child cr = new Child();
        Child cr1 = new Child();
        cr.checkReference();
        cr1.checkReference();

        Car car1 = new Car("Red");
        Car car2 = new Car("Blue");
        System.out.println("car1: " + car1);
        car1.printThis();
        System.out.println("car2: " + car2);
        car2.printThis();
    }
}
