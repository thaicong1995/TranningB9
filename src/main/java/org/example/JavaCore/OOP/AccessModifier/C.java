package org.example.JavaCore.OOP.AccessModifier;

import javax.swing.plaf.SpinnerUI;

public class C {

    private class A {
        public void display_A() {
            System.out.println("A1");
        }

        // Không gọi được dù đã được extend
        // muốn sử dụng thì cho vào 1 method public
        private void display_A1()
        {
            System.out.println("A1");
        }

        // Chỉ sủ dụng được trong cùng 1 package.
        protected  void display_A2()
        {
            System.out.println("A2");
        }
    }

    protected class B extends A {
//        protected void display_B() {
//            display_A();
//        }
    }

    public class C1 extends B {
        public void display() {
            display_A2();
            display_A();
        }
    }

    public class C2 extends A {

    }

}
