package com.example.usermanagement.service;

import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.repositories.PrivilegeRepository;
import com.example.usermanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Role createRoleWithPrivileges(Role role) {
        Set<Privilege> persistedPrivileges = new HashSet<>();
        for (Privilege privilege : role.getPrivileges()) {
            Privilege persistedPrivilege = privilegeRepository.findByCode(privilege.getCode());
            if (persistedPrivilege == null) {
                persistedPrivilege = privilegeRepository.save(privilege);
            }
            persistedPrivileges.add(persistedPrivilege);
        }
        role.setPrivileges(persistedPrivileges);
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = getRoleById(id);
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());

        Set<Privilege> persistedPrivileges = new HashSet<>();
        for (Privilege privilege : roleDetails.getPrivileges()) {
            Privilege persistedPrivilege = privilegeRepository.findByCode(privilege.getCode());
            if (persistedPrivilege == null) {
                persistedPrivilege = privilegeRepository.save(privilege);
            }
            persistedPrivileges.add(persistedPrivilege);
        }
        role.setPrivileges(persistedPrivileges);
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

