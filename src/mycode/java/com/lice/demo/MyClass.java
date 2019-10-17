package com.lice.demo;

/**
 * description: MyClass <br>
 * date: 2019/8/21 22:54 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class MyClass {

    private MyInterface myInterface;

    public void print() {
        myInterface.print();
    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.print();
    }
}
