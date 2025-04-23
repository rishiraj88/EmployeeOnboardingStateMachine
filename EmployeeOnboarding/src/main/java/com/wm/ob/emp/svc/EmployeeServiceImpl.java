package com.wm.ob.emp.svc;

import com.wm.ob.emp.dao.EmployeeRepo;
import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.entity.enums.EmployeeEvent;
import com.wm.ob.emp.entity.enums.EmployeeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wm.ob.emp.common.Constants.EMPLOYEE;
import static com.wm.ob.emp.common.Constants.EMPLOYEE_EMAIL_ADDRESS;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeDataRepo;

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private StateMachine<EmployeeState, EmployeeEvent> employeeStateMachine;
    public void setEmployeeStateMachine(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
        this.employeeStateMachine = stateMachine;
    }

    @Transactional
    public StateMachine<EmployeeState, EmployeeEvent> addEmployee(Employee employee) {
        employeeDataRepo.getEmployees().put(employee.getEmailAddress(), employee);
        employeeStateMachine = stateMachineService.initStateMachine(employee.getEmailAddress());
        return employeeStateMachine;
    }

    @Transactional
    public StateMachine<EmployeeState, EmployeeEvent> updateEmployee(String emailAddress, EmployeeEvent event) {
        employeeStateMachine = stateMachineService.initStateMachine(emailAddress);
        sendEvent(emailAddress, employeeStateMachine, event);
        return employeeStateMachine;
    }

    @Transactional
    public Employee getEmployeeDetails(String emailAddress) {
        return employeeDataRepo.getEmployees().get(emailAddress);
    }




    @Transactional
    public void sendEvent(String emailAddress, StateMachine<EmployeeState, EmployeeEvent> sm, EmployeeEvent employeeEvent) {
        Message message = MessageBuilder.withPayload(employeeEvent).setHeader(EMPLOYEE_EMAIL_ADDRESS, emailAddress).setHeader(EMPLOYEE, employeeDataRepo.getEmployees().get(emailAddress)) //for getting employee details optionally
                .build();
        sm.sendEvent(message);
        // employees.get(sm.getId()).setState();
    }

}
