package com.wm.ob.emp.data;

import com.wm.ob.emp.entity.Employee;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeDataRepo {
    private Map<String, Employee> employees = new HashMap<>();
    public Map<String, Employee> getEmployees() {
        return employees;
    }
}
