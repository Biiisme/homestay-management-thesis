package com.example.homestaymanager.service.impl;

import com.example.homestaymanager.dto.request.UpdateEmployee;
import com.example.homestaymanager.dto.response.EmployeeResponse;
import com.example.homestaymanager.dto.response.RoleResponse;
import com.example.homestaymanager.mapper.EmployeeMapper;
import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.repository.EmployeeRepository;
import com.example.homestaymanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    @Override
    public Integer createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee.getId();
    }

    @Override
    public EmployeeResponse getEmployeeByID(int id){

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        // map role
        RoleResponse roleRes = new RoleResponse();
        roleRes.setId(emp.getRole().getId());
        roleRes.setName(emp.getRole().getName());

        // map employee
        EmployeeResponse res = new EmployeeResponse();
        res.setId(emp.getId());
        res.setName(emp.getName());
        res.setEmail(emp.getEmail());
        res.setPhone(emp.getPhone());
        res.setAddress(emp.getAddress());
        res.setImage(emp.getImage());
        res.setRole(roleRes);

        return res;
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public ArrayList<Employee> getListEmployee() {
        return (ArrayList<Employee>) employeeRepository.findAll();
    }

//    @Override
//    public Employee  UpdateEmployeeById(int id, UpdateEmployee newEmp) {
//        Employee oldEmp = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy nhân viên"));
//        employeeMapper.updateEmployeeFromDto(newEmp,oldEmp);
//        return employeeRepository.save(oldEmp);
//    }
}
