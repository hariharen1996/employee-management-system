package com.ems.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ems.dto.Employee;
import com.ems.utils.EmployeeRowMapper;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee insert(Employee employee) {
        String sql = "insert into employees (firstname,lastname,email,phone,joining_date,department,salary,location) values(?,?,?,?,?,?,?,?)";
        Object[] data = {employee.getFirstName(),employee.getLastName(),employee.getEmail(),employee.getPhone(),employee.getJoiningDate(),employee.getDepartment().getDepartmentName(),employee.getSalary(),employee.getLocation()};
        jdbcTemplate.update(sql, data);
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "select * from employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }
    
}
