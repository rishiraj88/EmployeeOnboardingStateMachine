package com.wm.ob.emp.ctlr;

import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.svc.EmployeeSmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eob/api/")
public class EmployeeSmController {
    @Autowired
    private EmployeeSmService employeeSmService;

    // An Endpoint to add an employee
    @PostMapping("add")
    public void addEmployee(@RequestBody Employee employee) {
        employeeSmService.addEmployee(employee);
    }

    //An Endpoint to change the state of a given employee according to the state machine rules
    @PutMapping("update")
    public void UpdateEmployee(@RequestParam("email") String emailAddress, @RequestParam("event") String eventValue) {
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.valueOf(eventValue));
    }

    //An Endpoint to fetch employee details
    @GetMapping("getByEmailAddress")
    public Employee getEmployeeDetails(@RequestParam("email") String emailAddress) {
        return employeeSmService.getEmployeeDetails(emailAddress);
    }

}
