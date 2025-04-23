package com.wm.ob.emp.controller;

import com.wm.ob.emp.entity.Employee;
import com.wm.ob.emp.entity.enums.EmployeeEvent;
import com.wm.ob.emp.svc.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("api/v1/eob")
public class EmployeeController {
    private final EmployeeService employeeService;

    // Endpoint to add an employee
    @PostMapping()
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    // Endpoint to change the state of a given employee according to the rules of tenant (organisation)
    @PutMapping()
    public void updateEmployee(@RequestParam("email") String emailAddress, @RequestParam("event") String eventValue) {
        employeeService.updateEmployee(emailAddress, EmployeeEvent.valueOf(eventValue));
    }

    // Endpoint to fetch employee details
    @GetMapping()
    public Employee getEmployeeDetails(@RequestParam("email") String emailAddress) {
        return employeeService.getEmployeeDetails(emailAddress);
    }

}
