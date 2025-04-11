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
import com.ems.exceptions.ApiResponse;
import com.ems.exceptions.EmployeeCustomException;
import com.ems.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody @Valid Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("employee created successfully",savedEmployee));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ApiResponse.success(employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeesById(@PathVariable int id) {
        Employee employees = employeeService.getEmployeesById(id);
        if (employees != null) {
            return ResponseEntity.ok(ApiResponse.success(employees));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("employee id not found: " + id));
    }

    @GetMapping("/department")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeesByDepartment(@RequestParam String dept) {
        try{
            List<Employee> employees = employeeService.getEmployeesByDepartment(dept);
            if(employees.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("no employees found for the department: " + dept));
            
            }
            return ResponseEntity.ok(ApiResponse.success(employees));    
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeesEmail(@PathVariable String email) {
        Employee employees = employeeService.getEmployeesByEmail(email);
        if (employees != null) {
            return ResponseEntity.ok(ApiResponse.success(employees));  
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("employee email not found: " + email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable int id, @RequestBody @Valid Employee employee) {
        if (id != employee.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("id should match the path"));
        }

        try{
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            return ResponseEntity.ok(ApiResponse.success("employee updated successfully",updatedEmployee));  
  
        }catch(EmployeeCustomException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable int id) {
        boolean deletedEmployee = employeeService.deleteEmployee(id);
        if (deletedEmployee) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("employee deleted successfully",null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("employee id not found " + id));
    }

    @PostMapping("/create/bulk")
    public ResponseEntity<ApiResponse<Void>> bulkCreateEmployees(@RequestBody @Valid List<@Valid Employee> employees) {
        employeeService.bulkCreateEmployees(employees);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("successfully created employees in bulk",null));
    }

    @PutMapping("/update/bulk")
    public ResponseEntity<ApiResponse<Void>> bulkUpdateEmployees(@RequestBody @Valid List<@Valid Employee> employees) {
        employeeService.bulkUpdateEmployees(employees);
        return ResponseEntity.ok(ApiResponse.success("successfully updated employees in bulk",null));    
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployees(@RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSalary, @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) String phone) {
        List<Employee> employees = employeeService.searchEmployees(firstname, lastname, startDate, endDate, location,
                minSalary, maxSalary, phone);
            
                if(employees.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("no employees match found"));
                }
                return ResponseEntity.ok(ApiResponse.success(employees));
    }

}
