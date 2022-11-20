package com.wm.ob.emp.entity;

import com.wm.ob.emp.EmployeeState;
import com.wm.ob.emp.sm.EmployeeState;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Employee {
    String emailAddress; // used as identifier. Enough for simpler system design
    String name;
    String contract;
    int age;
    EmployeeState state = EmployeeState.ADDED;

    public Employee(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

