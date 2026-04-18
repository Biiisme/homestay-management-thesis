package com.example.homestaymanager.controller;

import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public Integer createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId){
        return employeeService.getEmployeeByID(employeeId);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployeeById(@PathVariable int employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }
}
