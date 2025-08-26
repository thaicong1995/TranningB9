package org.example.JavaCore.OOP.Abtract_Interface;

public class Classb extends Base1 implements IClassb, IClassc {

    @Override
    public void a() {
        System.out.println("I am a classb");
    }

    @Override
    public void b() {
        System.out.println("I am BBBB classb");
    }

    public void b(String a){
        System.out.println("I am c classb");
    }


    // K ghi đè được
//    @Override
//    public static int cc (){
//        return 2;
//    }

//    @Override
//    protected void h() {
//        System.out.println("Base2 h");
//    }
//
//    @Override
//    public void k() {
//        System.out.println("Base3 k");
//    }
//
//    @Override
//    protected void f() {
//        System.out.println("Base4 f");
//    }
//
//    @Override
//    public void g() {
//        System.out.println("Base5 g");
//    }
}
