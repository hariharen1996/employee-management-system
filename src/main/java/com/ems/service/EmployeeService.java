package com.ems.service;

import java.util.List;

import com.ems.dto.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
}
