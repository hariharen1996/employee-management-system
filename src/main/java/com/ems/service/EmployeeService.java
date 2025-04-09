package com.ems.service;

import java.util.List;

import com.ems.dto.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeesById(int id);
    Employee getEmployeesByEmail(String email);
    List<Employee> getEmployeesByDepartment(String department);
    Employee updateEmployee(Employee employee);
    boolean deleteEmployee(int id);
    void bulkCreateEmployees(List<Employee> employees);
    void bulkUpdateEmployees(List<Employee> employees);
}
