package org.example.JavaCore.Stactic_Final;

import static org.example.JavaCore.Stactic_Final.Stactic_Final.Static_Final_.PI;

public class Stactic_Final {
    public static void main(String[] args) {
        final C c_final = new C(2, "Cong");
        final String a = "First";
        final String b = "Second";
        System.out.println(PI);
        PI = 40;
        System.out.println("Square of PI: " + Static_Final_.square(PI));
        Static_Final_.Nested nested = new Static_Final_.Nested();
        nested.display();
        nested.display1();
        C c = new C(1, "thai");
        C c1 = new C(2, "nam");
        c.displayHashCode(a);
//        c.age =  lỗi final không cho gan lại giá trị khi đã có
        System.out.println(c.toString());
//        b = b + "aaa"; // Báo lỗi
        c1.displayHashCode(b);
        System.out.println(c1.toString());



    }

    public class Static_Final_ {
        // Static variable
         static double PI = 3.14159;
         int a = 1;
        // Static block
        // chạy 1 lần khi class được load vào JVM
        static {
            System.out.println("Static initializer when loaded" );
        }

        // Static method
        public static double square(double number) {
            return number * number;
        }
        // Static nested class
        public static class Nested implements B{
            public void display() {
                System.out.println(PI*PI);
            }

            public void display1() {
                var value = square(PI);
                System.out.println(value);
            }

            @Override
            public void show() {
                System.out.println("Show method in Nested class"  );
            }
        }

    }

    public static class A extends  Static_Final_.Nested {

    }

    public interface B {
        void show();
    }


}


