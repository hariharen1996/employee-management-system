package com.ems.dao;

import java.util.List;

import com.ems.dto.Employee;

public interface EmployeeDao {
    Employee insert(Employee employee);
    List<Employee> findAllEmployees();
    Employee findById(int id);
    Employee findByEmail(String email);
    List<Employee> findByDepartment(String department);
    boolean update(Employee employees);
    boolean delete(int id);
}

