package com.example.homestaymanager.service;

import com.example.homestaymanager.model.Employee;

public interface EmployeeService {
    Integer createEmployee(Employee employee);
    Employee getEmployeeByID(int id);
    public void deleteEmployeeById(int id);
}
