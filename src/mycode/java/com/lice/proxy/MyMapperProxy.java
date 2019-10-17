package com.lice.proxy;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * description: MyMapperProxy 使用java动态代理来创建mapper代理接口<br>
 * date: 2019/10/18 2:50 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class MyMapperProxy<T> implements InvocationHandler {

    private Class<T> mapperInterface;
    private SqlSession sqlSession;

    public MyMapperProxy(Class<T> mapperInterface,SqlSession sqlSession){
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //针对不同的SQL类型，调用不同的SqlSession的方法
        //接口的参数也有不同的，这只考虑没有参数的
        List<T> list = sqlSession.selectList(mapperInterface.getCanonicalName() + "." + method.getName());

        return list;
    }
}
