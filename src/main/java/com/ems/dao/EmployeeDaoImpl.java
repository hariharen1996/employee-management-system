package com.ems.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ems.dto.Department;
import com.ems.dto.Employee;
import com.ems.utils.EmployeeResultSetExtractor;
import com.ems.utils.EmployeeRowMapper;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee insert(Employee employee) {
        String sql = "insert into employees (firstname,lastname,email,phone,joining_date,department,salary,location) values(?,?,?,?,?,?,?,?)";
        Object[] data = {employee.getFirstName(),employee.getLastName(),employee.getEmail(),employee.getPhone(),employee.getJoiningDate(),employee.getDepartment().getId(),employee.getSalary(),employee.getLocation()};
        jdbcTemplate.update(sql, data);
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "select e.*, d.name as department_name from employees e join departments d on e.department = d.id";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    @Override
    public Employee findById(int id) {
        String sql = "select e.*, d.name as department_name from employees e join departments d on e.department = d.id where e.id = ?";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(),id);
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = "select e.*, d.name as department_name from employees e join departments d on e.department = d.id where e.email = ?";
        return jdbcTemplate.query(sql, new EmployeeResultSetExtractor(),email);
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        String departmentName = Department.getByName(department).getDepartmentName();
        String sql = "select e.*, d.name as department_name from employees e join departments d on e.department = d.id where lower(d.name) = lower(?)";
        return jdbcTemplate.query(sql, new EmployeeRowMapper(),departmentName);    
    }

    @Override
    public boolean update(Employee employee){
        String sql = "update employees set firstname = ?,lastname=?,email=?,phone=?,joining_date=?,department=?,salary=?,location=? where id = ?";
        Object[] data = {employee.getFirstName(),employee.getLastName(),employee.getEmail(),employee.getPhone(),employee.getJoiningDate(),employee.getDepartment().getId(),employee.getSalary(),employee.getLocation(),employee.getId()};
        int updated = jdbcTemplate.update(sql,data);
        return updated == 1;
    }

    @Override
    public boolean delete(int id){
        String sql = "delete from employees where id = ?";
        int deleted = jdbcTemplate.update(sql,id);
        return deleted == 1;
    }

    @Override
    public int[] batchInsert(List<Employee> employees) {
        String sql = "insert into employees (firstname,lastname,email,phone,joining_date,department,salary,location) values(?,?,?,?,?,?,?,?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Employee employee = employees.get(i);
                System.out.println(employee);
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setString(3, employee.getEmail());
                ps.setString(4, employee.getPhone());
                ps.setObject(5, employee.getJoiningDate());
                ps.setInt(6, employee.getDepartment().getId());
                ps.setDouble(7, employee.getSalary());
                ps.setString(8, employee.getLocation());
            }

            @Override
            public int getBatchSize() {
                return employees.size();
            } 
        });
    }

    @Override
    public int[] batchUpdates(List<Employee> employees) {
        String sql = "update employees set firstname = ?,lastname = ?,email = ?,phone = ?,joining_date = ?,department = ?,salary = ?,location = ? WHERE id = ?";        
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Employee employee = employees.get(i);
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setString(3, employee.getEmail());
                ps.setString(4, employee.getPhone());
                ps.setObject(5, employee.getJoiningDate());
                ps.setInt(6, employee.getDepartment().getId());
                ps.setDouble(7, employee.getSalary());
                ps.setString(8, employee.getLocation());
                ps.setInt(9, employee.getId());
            }

            @Override
            public int getBatchSize() {
                return employees.size();
            }
        });
    }

    @Override
    public List<Employee> searchEmployees(String firstName, String lastName, LocalDate startDate, LocalDate endDate,
            String location, Double minSalary, Double maxSalary,String phone) {
        StringBuilder sql = new StringBuilder("select e.*, d.name as department_name from employees e join departments d on e.department = d.id where 1 = 1");
        List<Object> stmt =  new ArrayList<>();

        if(firstName != null){
            sql.append("and lower(e.firstname) like lower(?)");
            stmt.add("%" + firstName + "%");
        }

        if(lastName != null){
            sql.append("and lower(e.lastname) like lower(?)");
            stmt.add("%" + lastName + "%");
        }

        if(startDate != null){
            sql.append("and e.joining_date >= ?");
            stmt.add(Date.valueOf(startDate));
        }

        if(endDate != null){
            sql.append("and e.joining_date <= ?");
            stmt.add(Date.valueOf(endDate));
        }

        if(location != null){
            sql.append("and lower(e.location) like lower(?)");
            stmt.add("%" + location + "%");
        }

        if(minSalary != null){
            sql.append("and e.salary >= ?");
            stmt.add(minSalary);
        }

        if(maxSalary != null){
            sql.append("and e.salary <= ?");
            stmt.add(maxSalary);
        }

        if(phone != null && !phone.trim().isEmpty()){
            sql.append("and e.phone = ?");
            stmt.add(phone.trim());
        }
        
        return jdbcTemplate.query(sql.toString(), new EmployeeRowMapper(),stmt.toArray());

    }
}
