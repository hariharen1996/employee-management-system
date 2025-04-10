package com.ems.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ems.dto.Employee;

@Component
public class EmployeeCache {
    private final Map<Integer,Employee> idCache = new HashMap<>();
    private final Map<String,Employee> emailCache = new HashMap<>();
    
    public void addCache(Employee employee){
        if(employee != null){
            idCache.put(employee.getId(), employee);
            emailCache.put(employee.getEmail(), employee);
        }
    }

    public Employee getFromCache(int id){
        return idCache.get(id);
    }

    public Employee getFromCache(String email){
        return emailCache.get(email);
    }

    public void removeFromCache(int id){
        Employee employee = idCache.remove(id);
        if(employee != null){
            emailCache.remove(employee.getEmail());
        }
    }

    public void refreshCache(List<Employee> employees){
        clearCache();
        employees.forEach(this::addCache);
    }

    public void clearCache(){
        idCache.clear();
        emailCache.clear();
    }
    
}
