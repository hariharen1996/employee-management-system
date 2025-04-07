package com.ems.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ems.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee insert(Employee employee) {
        String sql = "insert into employees (firstname,lastname,email,phone,joining_date,department,salary,location) values(?,?,?,?,?,?,?,?)";
        Object[] data = {employee.getFirstName(),employee.getLastName(),employee.getEmail(),employee.getPhone(),employee.getJoiningDate(),employee.getDepartment(),employee.getSalary(),employee.getLocation()};
        jdbcTemplate.update(sql, data);
        return employee;
    }
    
}
