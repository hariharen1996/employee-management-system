package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ems.dao.EmployeeDao;
import com.ems.dto.Employee;
import com.ems.exceptions.EmployeeCustomException;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if(employeeDao.findByEmail(employee.getEmail()) != null){
            throw new EmployeeCustomException("employee with this email " + employee.getEmail() + " already exists");
        }
        
        Employee insertEmployeeData = employeeDao.insert(employee);
        return insertEmployeeData;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeDao.findAllEmployees();
        return employees;
    }

    @Override
    public Employee getEmployeesById(int id) {
        Employee employee = employeeDao.findById(id);
        return employee;
    }

    @Override
    public Employee getEmployeesByEmail(String email) {
        Employee employee = employeeDao.findByEmail(email);
        return employee;
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeDao.findByDepartment(department);
        return employees;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if(employee != null && employee.getId() == 0){
            throw new EmployeeCustomException("employee id cannot be null");         
        }

        boolean isUpdated = employeeDao.update(employee);
        if(isUpdated){
            return employee;   
        }
        throw new EmployeeCustomException("failed to update employee with id: " + employee.getId());
    }

    @Override
    public boolean deleteEmployee(int id) {
        boolean deleted = employeeDao.delete(id);
        return deleted;
    }

    @Override
    public void bulkCreateEmployees(List<Employee> employees) {
        employeeDao.batchInsert(employees);
    }

    @Override
    public void bulkUpdateEmployees(List<Employee> employees) {
        employeeDao.batchUpdates(employees);
    }
}
