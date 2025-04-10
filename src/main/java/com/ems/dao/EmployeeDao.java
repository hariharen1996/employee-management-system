package com.ems.dao;

import java.time.LocalDate;
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
    int[] batchInsert(List<Employee> employees);
    int[] batchUpdates(List<Employee> employees);
    List<Employee> searchEmployees(String firstName, String lastName,LocalDate startDate,
    LocalDate endDate,String location,Double minSalary,Double maxSalary,String phone);
}

