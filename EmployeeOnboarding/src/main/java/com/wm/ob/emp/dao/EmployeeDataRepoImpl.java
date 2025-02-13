package com.wm.ob.emp.dao;

import com.wm.ob.emp.entity.Employee;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeDataRepoImpl implements EmployeeDataRepo {
    private Map<String, Employee> employees = new HashMap<>();
    public Map<String, Employee> getEmployees() {
        return employees;
    }
}
