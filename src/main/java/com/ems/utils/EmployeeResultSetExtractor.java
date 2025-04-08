package com.ems.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.ems.dto.Department;
import com.ems.dto.Employee;

public class EmployeeResultSetExtractor implements ResultSetExtractor<Employee> {

    @Override
    public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()){
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("firstname"));
            employee.setLastName(rs.getString("lastname"));
            employee.setEmail(rs.getString("email"));
            employee.setPhone(rs.getString("phone"));
            employee.setJoiningDate(rs.getObject("joining_date",LocalDate.class));
            employee.setSalary(rs.getDouble("salary"));
            employee.setLocation(rs.getString("location"));

            String departmentName = rs.getNString("department");
            employee.setDepartment(Department.getEnum(departmentName));
            
            return employee;
        }

        return null;
    }
    
}
