package com.lice.mapper;

import com.lice.pojo.Employee;
import com.sun.org.glassfish.external.probe.provider.annotations.ProbeProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description: EmployeeMapper接口 <br>
 * date: 2019/8/14 22:26 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
//@Mapper ：标注该接口是Mapper接口
public interface EmployeeMapper {

    //使用@Select注解来查询，等效于Mapper.xml配置中<select></select>
    // @Select("select emp_id as empId,emp_name as empName,emp_no as empNO,emp_birthday as empBirthday from tb_employee where emp_id = #{empId} ")
    public Employee getEmployeeById(Integer empId);

    //使用实现<resultMap>,可以复用的,如需要使用这个results，直接使用@ResultMap("employeeMap")引用即可
//    @Results(id = "employeeMap",value = {
//            @Result(property = "empId",column = "emp_id",id = true), //id=true相当于<id>标签
//            @Result(property = "empName",column = "emp_name"),
//            @Result(property = "empNo" ,column = "emp_no"),
//            @Result(property = "empBirthday" ,column = "emp_birthday")
//
//    })
    //@ResultMap("employeeMap")直接使用
    public List<Employee> selectAll();

    //    @Insert("insert into tb_employee(emp_id,emp_name,emp_no,emp_birthday) values(#{empId}, #{empName}, #{empNO}, #{empBirthday})")
//    //自增主键返回
//    @Options(useGeneratedKeys = true,keyProperty = "empId",keyColumn = "emp_id")
//    //非自增主键返回,before=true等同于order="before" before=false等同于order="after"
//    @SelectKey(statement = "SELECT LAST_INSERT_ID()",keyColumn = "emp_id",keyProperty = "empId",before = false,resultType = Integer.class)
    public int insertEmployee(Employee employee);

    public int updateEmployee(Employee employee);

    public int deleteEmployeeByIds(String[] empIds);


    //Provider 注解的使用
    /*
    MyBatis还提供了4种Provider注解，分别是：
    @ SelectProvider、
    @ InsertProvider、
    @ UpdateProvider
    @ DeleteProvider。
     */
    @SelectProvider(type = EmployeeProvider.class,method = "selectById")
    public Employee selectById(Integer empId);


}
