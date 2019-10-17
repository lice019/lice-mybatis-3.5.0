package com.lice.test;

import com.lice.mapper.EmployeeMapper;
import com.lice.pojo.Employee;
import com.lice.proxy.MyMapperProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * description: MyMapperProxy <br>
 * date: 2019/10/18 2:59 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class MyMapperProxyTest {

    public static void main(String[] args) throws Exception {

        String resource = "mybatis.cfg.xml";
        //读取mybatis.cfg.xml配置文件
        InputStream in = Resources.getResourceAsStream(resource);
        //创建sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //通过工厂生产SQLSession回话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        MyMapperProxy myMapperProxy = new MyMapperProxy(EmployeeMapper.class,sqlSession);
        EmployeeMapper employeeMapper = (EmployeeMapper)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{EmployeeMapper.class}, myMapperProxy);
        List<Employee> employees = employeeMapper.selectAll();
        System.out.println(employees);


    }
}
