package com.example.homestaymanager.service.impl;

import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.repository.EmployeeRepository;
import com.example.homestaymanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Integer createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee.getId();
    }

    @Override
    public Employee getEmployeeByID(int id) {
        return employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }
}
