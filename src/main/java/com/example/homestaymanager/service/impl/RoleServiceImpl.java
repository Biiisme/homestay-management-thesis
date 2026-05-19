package com.example.homestaymanager.service.impl;

import com.example.homestaymanager.dto.request.UpdateRoleRequest;
import com.example.homestaymanager.model.Role;
import com.example.homestaymanager.repository.RoleRepository;
import com.example.homestaymanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private static final String ADMIN_ROLE = "ADMIN";

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Integer createRole(Role role) {
        if (role == null || role.getName() == null || role.getName().isBlank()) {
            throw new RuntimeException("Ten vai tro la bat buoc");
        }
        if (isAdminName(role.getName())) {
            throw new RuntimeException("Khong duoc tao vai tro ADMIN tai day");
        }
        ensureRoleNameUnique(role.getName(), null);
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByID(int id) {
        return  roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    @Transactional
    public void deleteRoleById(int id) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Role not found"));
        if (isAdminName(role.getName())) {
            throw new RuntimeException("Khong duoc xoa vai tro ADMIN");
        }
        roleRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getListRole() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role updateRoleById(int id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Role not found"));
        if (isAdminName(role.getName())) {
            throw new RuntimeException("Khong duoc sua vai tro ADMIN");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            if (isAdminName(request.getName())) {
                throw new RuntimeException("Khong duoc doi vai tro thanh ADMIN");
            }
            ensureRoleNameUnique(request.getName(), id);
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        return roleRepository.save(role);
    }

    private void ensureRoleNameUnique(String name, Integer currentId) {
        roleRepository.findByNameIgnoreCase(name)
                .filter(role -> currentId == null || role.getId() != currentId)
                .ifPresent(role -> {
                    throw new RuntimeException("Ten vai tro da ton tai");
                });
    }

    private boolean isAdminName(String name) {
        return name != null && ADMIN_ROLE.equalsIgnoreCase(name.trim());
    }
}
