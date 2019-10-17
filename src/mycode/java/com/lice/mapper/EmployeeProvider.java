package com.lice.mapper;

import com.lice.pojo.Employee;
import org.apache.ibatis.jdbc.SQL;

/**
 * description: EmployeeProvider mybatis使用@Provider注解<br>
 * date: 2019/10/18 3:29 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class EmployeeProvider {

    public String selectById(final Integer empId) {
        return "select emp_id, emp_name,emp_no,emp_birthday,dep_no from tb_employee where emp_id=#{empId}";

    }


}
