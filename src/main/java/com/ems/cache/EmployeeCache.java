package com.ems.cache;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ems.dto.Employee;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class EmployeeCache {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeCache.class);
    private final Map<Integer,Employee> idCache = new ConcurrentHashMap<>();
    private final Map<String,Employee> emailCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        logger.info("employee cache initialized");
    }
    
    public void addCache(Employee employee){
        if(employee != null){
            idCache.put(employee.getId(), employee);
            emailCache.put(employee.getEmail(), employee);
            logger.debug("added employee to cache with id: {} and email: {}",employee.getId(),employee.getEmail());
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
            logger.debug("removed employee from cache with id: {} and email: {}",id,employee.getEmail());
        }
    }

    public void refreshCache(List<Employee> employees){
        Objects.requireNonNull(employees,"employee list cannot be null");
        clearCache();
        employees.forEach(this::addCache);
        logger.info("cache has been refreshed with {} employees",employees.size());
    }

    public void clearCache(){
        idCache.clear();
        emailCache.clear();
        logger.info("cache cleared");
    }

    @PreDestroy
    public void destroy(){
        logger.info("clearing employee cache before destroy");
        clearCache();
    }
    
}
