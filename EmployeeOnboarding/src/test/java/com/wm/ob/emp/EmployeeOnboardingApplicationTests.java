package com.wm.ob.emp;

import com.wm.ob.emp.data.EmployeeDataRepo;
import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.sm.EmployeeState;
import com.wm.ob.emp.svc.EmployeeSmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class EmployeeOnboardingApplicationTests {

    @Autowired
    EmployeeSmService employeeSmService;

    @Autowired
    EmployeeDataRepo employeeDataRepo;

    @Test
    void contextLoads() {
    }

    // ******* Happy Scenarios ******* //
	/*
🏃 Scenario 1 :
1. create an employee
2. Update state of the employee to `IN-CHECK`
3. Update substate of `IN-CHECK` state of the employee to `SECURITY_CHECK_FINISHED`
4. Update substate of `IN-CHECK` state of the employee to `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
5. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_FINISHED` (employee is auto-transitioned to `APPROVED` state)
6. Update state of the employee to `ACTIVE`
    */
    @Test
    void returnsActiveEmployee01() throws Exception {
        String emailAddress = "e001@email.com";
        Employee emp = new Employee(emailAddress);
        employeeSmService.addEmployee(emp);

        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.BEGIN_CHECK);
        log.debug("Emp: " + employeeDataRepo.getEmployees().get(emailAddress).getState());
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.FINISH_SECURITY_CHECK);
        log.debug("Emp: " + employeeDataRepo.getEmployees().get(emailAddress).getState());
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.COMPLETE_INITIAL_WORK_PERMIT_CHECK);
        log.debug("Emp: " + employeeDataRepo.getEmployees().get(emailAddress).getState());
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.FINISH_WORK_PERMIT_CHECK);
        log.debug("Emp: " + employeeDataRepo.getEmployees().get(emailAddress).getState());
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.ACTIVATE);
        log.debug("Emp: " + employeeDataRepo.getEmployees().get(emailAddress).getState());

        Assertions.assertEquals(EmployeeState.ACTIVE, employeeDataRepo.getEmployees().get(emailAddress).getState());
    }

 /*
🏃 Scenario 2 :
1. create an employee
2. Update state of the employee to `IN-CHECK`
3. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
4. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_FINISHED`
5. Update substate of `IN-CHECK` state the employee to `SECURITY_CHECK_FINISHED` (employee is auto-transitioned to `APPROVED` state)
6. Update state of the employee to `ACTIVE`
*/

/*
🏃 Scenario 3 :
1. create an employee
2. Update state of the employee to `IN-CHECK`
3. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
4. Update substate of `IN-CHECK` state the employee to `SECURITY_CHECK_FINISHED`
5. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_FINISHED` (employee is auto-transitioned to `APPROVED` state)
6. Update state of the employee to `ACTIVE`
*/


    // ~~~~~~~ Unhappy Scenarios ~~~~~~~ //
	/*
💣 Scenario 1 :
1. create an employee
2. Update state of the employee to `IN-CHECK`
3. Update substate of `IN-CHECK` state of the employee to `SECURITY_CHECK_FINISHED`
4. Update state of the employee to `ACTIVE`: ❗✋transition `IN-CHECK` -> `ACTIVE` is not allowed
	 */

	/*
💣  Scenario 2 :
1. create an employee
2. Update state of the employee to `IN-CHECK`
3. Update substate of `IN-CHECK` state the employee to `SECURITY_CHECK_FINISHED`
4. Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_FINISHED`: ❗️✋transition `WORK_PERMIT_CHECK_STARTED` -> `WORK_PERMIT_CHECK_FINISHED` is not allowed
	 */
}
