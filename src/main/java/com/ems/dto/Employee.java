package com.ems.dto;

import java.time.LocalDate;

import com.ems.validations.ValidateEmail;
import com.ems.validations.ValidateLocation;
import com.ems.validations.ValidateSalary;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Employee {

    private int id;

    @NotBlank(message = "firstname is required")
    @Size(min = 4, max = 50, message = "firstname must be between 4 and 50 characters")
    private String firstName;

    @NotBlank(message = "lastname is required")
    @Size(min = 4, max = 50, message = "lastname must be between 4 and 50 characters")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "enter valid email")
    @ValidateEmail(domainsAllowed = {"gmail.com"},message = "domain named as 'gmail.com' are only allowed")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "phonenumber must be in 10 digits")
    private String phone;

    @NotNull(message = "joiningdate is required")
    @PastOrPresent(message = "joining date must be in past or present")
    private LocalDate joiningDate;

    @NotNull(message = "department is required")
    private Department department;

    @NotNull(message = "salary is required")
    @ValidateSalary(min = 5000.0,message = "salary must be atleast 5000")
    private Double salary;

    @NotNull(message = "location is required")
    @ValidateLocation(message = "These locations are only allowed - madurai,chennai,bengaluru,coimbatore,delhi,mumbai,hyderabad")
    private String location;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String email, String phone, LocalDate joiningDate,
            Department department, Double salary, String location) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.joiningDate = joiningDate;
        this.department = department;
        this.salary = salary;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", phone=" + phone + ", joiningDate=" + joiningDate + ", department=" + department + ", salary="
                + salary + ", location=" + location + "]";
    }

}
