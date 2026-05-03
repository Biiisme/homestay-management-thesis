package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.model.Role;
import com.example.homestaymanager.service.EmployeeService;
import com.example.homestaymanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/roles")
    public ApiResponse<Integer> createRole(@RequestBody Role role){
        int i= roleService.createRole(role);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,role.getId());
    }

    @GetMapping("/roles/{roleId}")
    public ApiResponse<Role> getRoleById(@PathVariable int roleId){
        Role role= roleService.getRoleByID(roleId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,role);
    }

    @DeleteMapping("/role/{roleId}")
    public ApiResponse<?> deleteRoleById(@PathVariable int roleId){
        roleService.deleteRoleById(roleId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
