package com.wm.ob.emp.svc;

import com.wm.ob.emp.entity.enums.EmployeeEvent;
import com.wm.ob.emp.entity.enums.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface StateMachineService {
    StateMachine<EmployeeState, EmployeeEvent> initStateMachine(String employeeEmailAddress);
    StateMachine<EmployeeState, EmployeeEvent> getStateMachine();
}
