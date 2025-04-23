package com.wm.ob.emp.svc;

import com.wm.ob.emp.config.EmployeeEventInterceptor;
import com.wm.ob.emp.entity.enums.EmployeeEvent;
import com.wm.ob.emp.entity.enums.EmployeeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;

public class StateMachineServiceImpl implements StateMachineService {
    @Autowired
    private StateMachineFactory<EmployeeState, EmployeeEvent> factory;

    @Autowired
    private EmployeeEventInterceptor employeeEventInterceptor;

    private StateMachine<EmployeeState, EmployeeEvent> stateMachine;

    @Override
    public StateMachine<EmployeeState, EmployeeEvent> getStateMachine() {
        return stateMachine;
    }

    @Override
    public StateMachine<EmployeeState, EmployeeEvent> initStateMachine(String employeeEmailAddress) {
        if (null != stateMachine) return stateMachine;
        stateMachine = factory.getStateMachine(employeeEmailAddress);
        stateMachine.stopReactively();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(employeeEventInterceptor);
            sma.resetStateMachineReactively(new DefaultStateMachineContext<>(EmployeeState.ADDED, null, null, null));
        });
        stateMachine.startReactively();
        return stateMachine;
    }

}
