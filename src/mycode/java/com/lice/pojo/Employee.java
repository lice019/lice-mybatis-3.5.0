package com.lice.pojo;


import org.apache.ibatis.type.Alias;

import java.util.Date;
//@Alias("employee"):通过注解来进行别名
public class Employee {

    private Integer empId;
    private String empName;
    private String empNo;
    private Date empBirthday;
    private String depNo;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Date getEmpBirthday() {
        return empBirthday;
    }

    public void setEmpBirthday(Date empBirthday) {
        this.empBirthday = empBirthday;
    }

    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empNo='" + empNo + '\'' +
                ", empBirthday=" + empBirthday +
                ", depNo='" + depNo + '\'' +
                '}';
    }
}
