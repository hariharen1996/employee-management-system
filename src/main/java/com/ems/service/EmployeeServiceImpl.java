package com.ems.service;

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
    
}
