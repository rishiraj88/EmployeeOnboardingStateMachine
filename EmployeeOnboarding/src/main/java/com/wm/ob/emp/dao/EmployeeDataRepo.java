package com.wm.ob.emp.dao;

import com.wm.ob.emp.entity.Employee;

import java.util.Map;

public interface EmployeeDataRepo {
    public Map<String, Employee> getEmployees();
}
