package com.lice.demo;

/**
 * description: MyInterfaceImpl <br>
 * date: 2019/8/21 22:53 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class MyInterfaceImpl implements MyInterface {
    @Override
    public void print() {
        System.out.println("实现类的方法被调用....");
    }
}
