package com.wm.ob.emp.entity;

import com.wm.ob.emp.entity.enums.EmployeeState;
import lombok.Data;

@Data
public class Employee {
    String emailAddress; // used as identifier. Enough for simpler system designs
    String name;
    String contract;
    int age;
    EmployeeState state = EmployeeState.ADDED;

    public Employee(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

