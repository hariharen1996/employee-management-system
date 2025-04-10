package com.ems.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.Employee;
import com.ems.exceptions.EmployeeCustomException;
import com.ems.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeesById(@PathVariable int id) {
        Employee employees = employeeService.getEmployeesById(id);
        if (employees != null) {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam String dept) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(dept);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeesEmail(@PathVariable String email) {
        Employee employees = employeeService.getEmployeesByEmail(email);
        if (employees != null) {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        if (id != employee.getId()) {
            throw new EmployeeCustomException("id should match the path");
        }

        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        boolean deletedEmployee = employeeService.deleteEmployee(id);
        if (deletedEmployee) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create/bulk")
    public ResponseEntity<Void> bulkCreateEmployees(@RequestBody List<Employee> employees) {
        employeeService.bulkCreateEmployees(employees);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/bulk")
    public ResponseEntity<Void> bulkUpdateEmployees(@RequestBody List<Employee> employees) {
        employeeService.bulkUpdateEmployees(employees);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSalary, @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) String phone) {
        List<Employee> employees = employeeService.searchEmployees(firstname, lastname, startDate, endDate, location,
                minSalary, maxSalary, phone);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
