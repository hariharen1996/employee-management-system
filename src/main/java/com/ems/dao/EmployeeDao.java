package com.ems.dao;

import java.util.List;

import com.ems.dto.Employee;

public interface EmployeeDao {
    Employee insert(Employee employee);
    List<Employee> findAllEmployees();
}

