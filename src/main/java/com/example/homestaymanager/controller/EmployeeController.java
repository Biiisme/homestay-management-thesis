package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.request.CreateEmployeeRequest;
import com.example.homestaymanager.dto.request.UpdateEmployee;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.dto.response.EmployeeResponse;
import com.example.homestaymanager.exception.UnauthorizedException;
import com.example.homestaymanager.security.SecurityUtil;
import com.example.homestaymanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ApiResponse<ArrayList<EmployeeResponse>> getListEmployee() {
        if (!SecurityUtil.isAdmin()) {
            throw new UnauthorizedException("Chi admin co the xem danh sach nhan vien");
        }
        ArrayList<EmployeeResponse> employees = employeeService.getListEmployee();
        return ApiResponse.of(ApiStatus.OK, "Lay ra danh sach nhan vien thanh cong", employees);
    }

    @PostMapping("/employees")
    public ApiResponse<?> createEmployee(@RequestBody CreateEmployeeRequest employee) {
        if (!SecurityUtil.isAdmin()) {
            throw new UnauthorizedException("Chi admin co the tao nhan vien");
        }
        employeeService.createEmployee(employee);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED, null);
    }

    @GetMapping("/employees/{employeeId}")
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable int employeeId) {
        if (!SecurityUtil.isAdmin()) {
            var current = SecurityUtil.getCurrentUser();
            if (current == null || current.getId() != employeeId) {
                throw new UnauthorizedException("Khong co quyen xem thong tin nhan vien nay");
            }
        }
        EmployeeResponse employee = employeeService.getEmployeeByID(employeeId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, employee);
    }

    @PatchMapping("/employees/{employeeId}")
    public ApiResponse<EmployeeResponse> updateEmployeeByID(@PathVariable int employeeId, @RequestBody UpdateEmployee dtoEmp) {
        if (!SecurityUtil.isAdmin()) {
            var current = SecurityUtil.getCurrentUser();
            if (current == null || current.getId() != employeeId) {
                throw new UnauthorizedException("Khong co quyen sua thong tin nhan vien nay");
            }
        }
        EmployeeResponse employee = employeeService.UpdateEmployeeById(employeeId, dtoEmp);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.UPDATED, employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ApiResponse<?> deleteEmployeeById(@PathVariable int employeeId) {
        if (!SecurityUtil.isAdmin()) {
            throw new UnauthorizedException("Chi admin co the xoa nhan vien");
        }
        employeeService.deleteEmployeeById(employeeId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED, null);
    }
}
