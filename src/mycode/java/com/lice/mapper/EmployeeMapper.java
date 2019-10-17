package com.lice.mapper;

import com.lice.pojo.Employee;

/**
 * description: EmployeeMapper接口 <br>
 * date: 2019/8/14 22:26 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public interface EmployeeMapper {

    public Employee getEmployeeById(String empId);
    public int insertEmployee(Employee employee);
    public int updateEmployee(Employee employee);
    public int deleteEmployeeByIds(String [] empIds);

}
