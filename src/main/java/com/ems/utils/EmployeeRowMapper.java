package com.ems.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.ems.dto.Department;
import com.ems.dto.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setLastName(rs.getString("lastname"));
        employee.setEmail(rs.getString("email"));
        employee.setPhone(rs.getString("phone"));
        employee.setJoiningDate(rs.getObject("joining_date",LocalDate.class));
        employee.setSalary(rs.getDouble("salary"));
        employee.setLocation(rs.getString("location"));

        String departmentName = rs.getNString("department");
        employee.setDepartment(Department.getEnum(departmentName));
        
        return employee;
    } 
}
