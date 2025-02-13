package com.wm.ob.emp.svc;

import com.wm.ob.emp.config.EmployeeEventInterceptor;
import com.wm.ob.emp.dao.EmployeeRepo;
import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.sm.EmployeeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wm.ob.emp.common.Constants.EMPLOYEE;
import static com.wm.ob.emp.common.Constants.EMPLOYEE_EMAIL_ADDRESS;

@Service
public class EmployeeSmService {

    @Autowired
    private StateMachineFactory<EmployeeState, EmployeeEvent> factory;

    @Autowired
    private EmployeeEventInterceptor employeeEventInterceptor;

    private StateMachine<EmployeeState,EmployeeEvent> stateMachine;

    @Autowired
    private EmployeeRepo employeeDataRepo;

    @Transactional
    public StateMachine<EmployeeState,EmployeeEvent>  addEmployee(Employee employee) {
        employeeDataRepo.getEmployees().put(employee.getEmailAddress(), employee);
        stateMachine = initStateMachine(employee.getEmailAddress());
        return stateMachine;

    }
    @Transactional
    public StateMachine<EmployeeState,EmployeeEvent> UpdateEmployee(String emailAddress, EmployeeEvent event) {
        stateMachine = initStateMachine(emailAddress);
        sendEvent(emailAddress,stateMachine,event);
        return stateMachine;
    }
    @Transactional
    public Employee getEmployeeDetails(String emailAddress) {
        return employeeDataRepo.getEmployees().get(emailAddress);
    }

    public StateMachine<EmployeeState,EmployeeEvent> getStateMachine() {
        return stateMachine;
    }
    public void setStateMachine(StateMachine<EmployeeState,EmployeeEvent> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Transactional
    public void sendEvent(String emailAddress,StateMachine<EmployeeState,EmployeeEvent> sm, EmployeeEvent employeeEvent) {
        Message message = MessageBuilder.withPayload(employeeEvent)
                .setHeader(EMPLOYEE_EMAIL_ADDRESS, emailAddress)
                .setHeader(EMPLOYEE,employeeDataRepo.getEmployees().get(emailAddress)) //for getting employee details optionally
                .build();
        sm.sendEvent(message);
//        employees.get(sm.getId()).setState();
    }
    private StateMachine<EmployeeState,EmployeeEvent> initStateMachine(String employeeEmailAddress) {
        if(null != stateMachine) return stateMachine;
        stateMachine = factory.getStateMachine(employeeEmailAddress);
        stateMachine.stopReactively();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions( sma -> {
                            sma.addStateMachineInterceptor(employeeEventInterceptor);
                            sma.resetStateMachineReactively(
                                    new DefaultStateMachineContext<>(EmployeeState.ADDED,null,null,null)
                            );
                        }

                );
        stateMachine.startReactively();
        return  stateMachine;
    }
}
