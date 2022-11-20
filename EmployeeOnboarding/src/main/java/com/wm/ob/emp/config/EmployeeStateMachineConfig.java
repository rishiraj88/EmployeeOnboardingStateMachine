package com.wm.ob.emp.config;


import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.sm.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class EmployeeStateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
        states.withStates()
                .initial(EmployeeState.ADDED)
                .states(EnumSet.allOf(EmployeeState.class))
                .end(EmployeeState.ACTIVE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
        transitions
                // ADDED -> IN CHECK
                .withExternal().source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK).event(EmployeeEvent.BEGIN_CHECK)
                .action(startBothChecks())
                .and()

                // setting "work permit check" to 'pending verification'
                .withExternal().source(EmployeeState.IN_CHECK).target(EmployeeState.IN_CHECK).event(EmployeeEvent.COMPLETE_INITIAL_WORK_PERMIT_CHECK)
                .guard(guardOnWorkPermitPendingVerification())
                .action(completeInitialWorkPermitCheck())
                .and()

                //finishing "security check"
                .withExternal().source(EmployeeState.IN_CHECK).target(EmployeeState.IN_CHECK).event(EmployeeEvent.FINISH_SECURITY_CHECK)
                .action(finishSecurityCheck())
                .and()

                //finishing "work permit check"
                .withExternal().source(EmployeeState.IN_CHECK).target(EmployeeState.IN_CHECK).event(EmployeeEvent.FINISH_WORK_PERMIT_CHECK)
                .action(finishWorkPermitCheck())
                .and()
                
                //to APPROVED upon finishing "security check"
                .withExternal().source(EmployeeState.IN_CHECK).target(EmployeeState.APPROVED) //.event(EmployeeEvent.FINISH_SECURITY_CHECK)
                .guard(guardOnAllChecksFinished())
                .and()

                //to APPROVED upon finishing "work permit check"
                .withExternal().source(EmployeeState.IN_CHECK).target(EmployeeState.APPROVED) //.event(EmployeeEvent.FINISH_WORK_PERMIT_CHECK)
                .guard(guardOnAllChecksFinished())
                .and()

                // APPROVED -> ACTIVE
                .withExternal().source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeEvent.ACTIVATE)
        ;
    }

    @Bean
    public Action<EmployeeState, EmployeeEvent> startBothChecks() {
        return new Action<EmployeeState, EmployeeEvent>() {

            @Override
            public void execute(StateContext<EmployeeState, EmployeeEvent> context) {
                System.out.println("Setting SecurityCheck to 'started'");
                context.getExtendedState()
                        .getVariables().put("security check state", "started");
                System.out.println("Setting InitialWorkPermitCheck to 'started'");
                context.getExtendedState()
                        .getVariables().put("work permit check state", "started");

            }
        };
    }

    @Bean
    public Guard<EmployeeState, EmployeeEvent> guardOnWorkPermitPendingVerification() {
        return new Guard<EmployeeState, EmployeeEvent>() {
            @Override
            public boolean evaluate(StateContext<EmployeeState, EmployeeEvent> context) {
//                return true;
                System.out.println("Guard on work permit check...");
                return "started".equals(
                        context.getExtendedState()
                                .getVariables().get("work permit check state"));
            }
        };
    }

    @Bean
    public Guard<EmployeeState, EmployeeEvent> guardOnAllChecksFinished() {
        return new Guard<EmployeeState, EmployeeEvent>() {

            @Override
            public boolean evaluate(StateContext<EmployeeState, EmployeeEvent> context) {
//                return true;
                System.out.println("Guard on SecurityChecks...");
                return "done".equals(
                        context.getExtendedState()
                                .getVariables().get("security check state"))
                        &&
                        "done".equals(
                                context.getExtendedState()
                                        .getVariables().get("work permit check state"));
            }
        };
    }

    @Bean
    public Action<EmployeeState, EmployeeEvent> finishSecurityCheck() {
        return new Action<EmployeeState, EmployeeEvent>() {
            @Override
            public void execute(StateContext<EmployeeState, EmployeeEvent> context) {
                System.out.println("Setting SecurityCheck to 'done'");
                context.getExtendedState()
                        .getVariables().put("security check state", "done");
            }
        };
    }

    @Bean
    public Action<EmployeeState, EmployeeEvent> completeInitialWorkPermitCheck() {
        return new Action<EmployeeState, EmployeeEvent>() {
            @Override
            public void execute(StateContext<EmployeeState, EmployeeEvent> context) {
                System.out.println("Setting WorkPermitCheck to 'pending'");
                context.getExtendedState()
                        .getVariables().put("work permit check state", "pending");
            }
        };
    }

    @Bean
    public Action<EmployeeState, EmployeeEvent> finishWorkPermitCheck() {
        return new Action<EmployeeState, EmployeeEvent>() {
            @Override
            public void execute(StateContext<EmployeeState, EmployeeEvent> context) {
                System.out.println("finishWorkPermitCheck done!");
                context.getExtendedState()
                        .getVariables().put("work permit check state", "done");
            }
        };
    }
}