package com.example.homestaymanager.service;

import com.example.homestaymanager.dto.request.UpdateEmployee;
import com.example.homestaymanager.dto.request.CreateEmployeeRequest;
import com.example.homestaymanager.dto.response.EmployeeResponse;

import java.util.ArrayList;

public interface EmployeeService {
    Integer createEmployee(CreateEmployeeRequest employee);
    public void deleteEmployeeById(int id);
    ArrayList<EmployeeResponse> getListEmployee();
    EmployeeResponse  UpdateEmployeeById(int id, UpdateEmployee dtoEmp );
    EmployeeResponse getEmployeeByID(int id);
}
