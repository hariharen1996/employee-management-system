package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.dao.EmployeeDao;
import com.ems.dto.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Employee insertEmployeeData = employeeDao.insert(employee);
        return insertEmployeeData;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeDao.findAllEmployees();
        return employees;
    }
    
}
