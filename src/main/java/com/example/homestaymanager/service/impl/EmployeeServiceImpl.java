package com.example.homestaymanager.service.impl;

import com.example.homestaymanager.dto.request.CreateEmployeeRequest;
import com.example.homestaymanager.dto.request.UpdateEmployee;
import com.example.homestaymanager.dto.response.EmployeeResponse;
import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.model.Role;
import com.example.homestaymanager.repository.CustomerRepository;
import com.example.homestaymanager.repository.EmployeeRepository;
import com.example.homestaymanager.repository.RoleRepository;
import com.example.homestaymanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    private static final String ADMIN_ROLE = "ADMIN";

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Integer createEmployee(CreateEmployeeRequest request) {
        if (request == null) {
            throw new RuntimeException("Thong tin nhan vien la bat buoc");
        }
        requireNotBlank(request.getName(), "Ten nhan vien la bat buoc");
        requireNotBlank(request.getEmail(), "Email la bat buoc");
        requireNotBlank(request.getUsername(), "Username la bat buoc");
        requireNotBlank(request.getPassword(), "Mat khau la bat buoc");
        requireNotBlank(request.getPhone(), "So dien thoai la bat buoc");

        ensureEmailUnique(request.getEmail(), null);
        ensureUsernameUnique(request.getUsername(), null);

        Role role = resolveEmployeeRole(request.getRoleId());

        Employee employee = Employee.builder()
                .name(request.getName())
                .salary(request.getSalary())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .phone(request.getPhone())
                .address(request.getAddress())
                .image(request.getImage())
                .active(true)
                .role(role)
                .build();

        employeeRepository.save(employee);
        return employee.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeByID(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nhan vien"));
        return toResponse(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        int updatedRows = employeeRepository.disableById(id);
        if (updatedRows == 0) {
            throw new RuntimeException("Employee not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<EmployeeResponse> getListEmployee() {
        return employeeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public EmployeeResponse UpdateEmployeeById(int id, UpdateEmployee newEmp) {
        Employee oldEmp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nhan vien"));

        if (newEmp.getName() != null && !newEmp.getName().isBlank()) {
            oldEmp.setName(newEmp.getName());
        }

        if (newEmp.getSalary() != null) {
            oldEmp.setSalary(newEmp.getSalary());
        }

        if (newEmp.getEmail() != null && !newEmp.getEmail().isBlank()) {
            ensureEmailUnique(newEmp.getEmail(), id);
            oldEmp.setEmail(newEmp.getEmail());
        }

        if (newEmp.getUsername() != null && !newEmp.getUsername().isBlank()) {
            ensureUsernameUnique(newEmp.getUsername(), id);
            oldEmp.setUsername(newEmp.getUsername());
        }

        if (newEmp.getPhone() != null && !newEmp.getPhone().isBlank()) {
            oldEmp.setPhone(newEmp.getPhone());
        }

        if (newEmp.getAddress() != null) {
            oldEmp.setAddress(newEmp.getAddress());
        }

        if (newEmp.getImage() != null) {
            oldEmp.setImage(newEmp.getImage());
        }

        if (newEmp.getActive() != null) {
            oldEmp.setActive(newEmp.getActive());
        }

        if (newEmp.getRoleId() != null) {
            Role role = roleRepository.findById(newEmp.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role khong ton tai"));
            if (isAdminRole(role)) {
                throw new RuntimeException("Khong duoc cap role ADMIN cho nhan vien");
            }
            oldEmp.setRole(role);
        }

        employeeRepository.save(oldEmp);
        return toResponse(oldEmp);
    }

    private EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setSalary(employee.getSalary());
        response.setEmail(employee.getEmail());
        response.setUsername(employee.getUsername());
        response.setPhone(employee.getPhone());
        response.setAddress(employee.getAddress());
        response.setImage(employee.getImage());
        response.setActive(employee.getActive());
        response.setRole(employee.getRole());
        return response;
    }

    private Role resolveEmployeeRole(Integer roleId) {
        Role role = roleId == null
                ? roleRepository.findByNameIgnoreCase(EMPLOYEE_ROLE)
                    .orElseThrow(() -> new RuntimeException("Role EMPLOYEE khong ton tai"))
                : roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role khong ton tai"));

        if (isAdminRole(role)) {
            throw new RuntimeException("Khong duoc tao nhan vien voi role ADMIN");
        }
        return role;
    }

    private boolean isAdminRole(Role role) {
        return role.getName() != null && ADMIN_ROLE.equalsIgnoreCase(role.getName());
    }

    private void ensureEmailUnique(String email, Integer currentId) {
        employeeRepository.findByEmail(email)
                .filter(employee -> currentId == null || employee.getId() != currentId)
                .ifPresent(employee -> {
                    throw new RuntimeException("Email da ton tai");
                });
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email da ton tai");
        }
    }

    private void ensureUsernameUnique(String username, Integer currentId) {
        employeeRepository.findByUsername(username)
                .filter(employee -> currentId == null || employee.getId() != currentId)
                .ifPresent(employee -> {
                    throw new RuntimeException("Username da ton tai");
                });
    }

    private void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(message);
        }
    }
}
