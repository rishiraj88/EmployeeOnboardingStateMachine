package com.wm.ob.emp.dao;

import com.wm.ob.emp.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmployeeRepoImpl implements EmployeeRepo {
    private Map<String, Employee> employees = new ConcurrentHashMap<>();
    public Map<String, Employee> getEmployees() {
        return employees;
    }
}
