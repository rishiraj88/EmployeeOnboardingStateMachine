package com.wm.ob.emp.dao;

import com.wm.ob.emp.entity.Employee;

import java.util.Map;

public interface EmployeeRepo {
    public Map<String, Employee> getEmployees();
}
