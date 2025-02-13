package com.wm.ob.emp.config;

import com.wm.ob.emp.dao.EmployeeRepo;
import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.sm.EmployeeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static com.wm.ob.emp.common.Constants.EMPLOYEE_EMAIL_ADDRESS;

@Component
public class EmployeeEventInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeEvent> {

    @Autowired
    EmployeeRepo employeeDataRepo;

    @Override
    public void preStateChange(State<EmployeeState, EmployeeEvent> state, Message<EmployeeEvent> message, Transition<EmployeeState, EmployeeEvent> transition,
                               StateMachine<EmployeeState, EmployeeEvent> stateMachine, StateMachine<EmployeeState, EmployeeEvent> rootStateMachine) {

        Optional.ofNullable(message).ifPresent(msg -> {
                    Optional.ofNullable(String.class.cast(msg.getHeaders().getOrDefault(EMPLOYEE_EMAIL_ADDRESS, "e@email.com")))
                            .ifPresent(emailAddr -> {
                                Employee emp = employeeDataRepo.getEmployees().get(emailAddr);
                                emp.setState(state.getId());
                            });
                }
        );
    }
}
