package com.lice.test;

import com.lice.mapper.EmployeeMapper;
import com.lice.pojo.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.text.DateFormatter;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.List;

/**
 * description: 测试mybatis接口 <br>
 * date: 2019/8/14 22:40 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class App {

    public static void main1(String[] args) throws Exception {
        String resource = "mybatis.cfg.xml";
        //读取mybatis.cfg.xml配置文件
        InputStream in = Resources.getResourceAsStream(resource);
        //创建sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //通过工厂生产SQLSession回话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取mapper接口
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        //执行mapper接口
        Employee employee = employeeMapper.getEmployeeById(1);
        System.out.println(employee);

    }

    public static void main(String[] args) throws Exception {
        String resource = "mybatis.cfg.xml";
        //读取mybatis.cfg.xml配置文件
        InputStream in = Resources.getResourceAsStream(resource);
        //创建sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //通过工厂生产SQLSession回话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        Employee employee = sqlSession.selectOne("com.lice.mapper.EmployeeMapper.getEmployeeById", 1);
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employees = employeeMapper.selectAll();
        employees.forEach((item-> System.out.println(item)));
//        System.out.println(employeeMapper);

    }


}
