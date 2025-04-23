package com.wm.ob.emp.svc;

import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.entity.enums.EmployeeEvent;
import com.wm.ob.emp.entity.enums.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface EmployeeService {
    StateMachine<EmployeeState, EmployeeEvent> addEmployee(Employee employee);
    StateMachine<EmployeeState, EmployeeEvent> updateEmployee(String emailAddress, EmployeeEvent employeeEvent);
    Employee getEmployeeDetails(String emailAddress);

    void setEmployeeStateMachine(StateMachine<EmployeeState, EmployeeEvent> sm);
}
