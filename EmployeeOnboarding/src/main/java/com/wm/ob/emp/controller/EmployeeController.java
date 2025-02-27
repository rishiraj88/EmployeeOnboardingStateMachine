package com.wm.ob.emp.controller;

import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.sm.EmployeeEvent;
import com.wm.ob.emp.svc.EmployeeSmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/eob")
public class EmployeeController {
    @Autowired
    private EmployeeSmService employeeSmService;

    // An Endpoint to add an employee
    @PostMapping()
    public void addEmployee(@RequestBody Employee employee) {
        employeeSmService.addEmployee(employee);
    }

    //An Endpoint to change the state of a given employee according to the state machine rules
    @PutMapping()
    public void UpdateEmployee(@RequestParam("email") String emailAddress, @RequestParam("event") String eventValue) {
        employeeSmService.UpdateEmployee(emailAddress, EmployeeEvent.valueOf(eventValue));
    }

    //An Endpoint to fetch employee details
    @GetMapping()
    public Employee getEmployeeDetails(@RequestParam("email") String emailAddress) {
        return employeeSmService.getEmployeeDetails(emailAddress);
    }

}
