package com.ems.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ems.cache.EmployeeCache;
import com.ems.dao.EmployeeDao;
import com.ems.dto.Employee;
import com.ems.exceptions.EmployeeCustomException;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeCache cache;

    public EmployeeServiceImpl(EmployeeDao employeeDao,EmployeeCache cache){
        this.employeeDao = employeeDao;
        this.cache = cache;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if(employeeDao.findByEmail(employee.getEmail()) != null){
            throw new EmployeeCustomException("employee with this email " + employee.getEmail() + " already exists");
        }
        
        Employee insertEmployeeData = employeeDao.insert(employee);
        cache.addCache(insertEmployeeData);
        return insertEmployeeData;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeDao.findAllEmployees();
        cache.refreshCache(employees);
        return employees;
    }

    @Override
    public Employee getEmployeesById(int id) {
        Employee cachedEmployee = cache.getFromCache(id);
        if(cachedEmployee != null){
            return cachedEmployee;
        }
        Employee employee = employeeDao.findById(id);
        if(employee != null){
            cache.addCache(employee);
        }
        return employee;
    }

    @Override
    public Employee getEmployeesByEmail(String email) {
        Employee cachedEmployee = cache.getFromCache(email);
        if(cachedEmployee != null){
            return cachedEmployee;
        }
        Employee employee = employeeDao.findByEmail(email);
        if(employee != null){
            cache.addCache(employee);
        }
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
            cache.addCache(employee);
            return employee;   
        }
        throw new EmployeeCustomException("failed to update employee with id: " + employee.getId());
    }

    @Override
    public boolean deleteEmployee(int id) {
        boolean deleted = employeeDao.delete(id);
        if(deleted){
            cache.removeFromCache(id);
        }
        return deleted;
    }

    @Override
    public void bulkCreateEmployees(List<Employee> employees) {
        employeeDao.batchInsert(employees);
        employees.forEach(cache::addCache);
    }

    @Override
    public void bulkUpdateEmployees(List<Employee> employees) {
        employeeDao.batchUpdates(employees);
        employees.forEach(cache::addCache);
    }

    @Override
    public List<Employee> searchEmployees(String firstName, String lastName, LocalDate startDate, LocalDate endDate,
            String location, Double minSalary, Double maxSalary,String phone) {
        List<Employee> employees = employeeDao.searchEmployees(firstName, lastName, startDate, endDate, location, minSalary, maxSalary,phone);
        cache.refreshCache(employees);
        return employees;
    }
}
