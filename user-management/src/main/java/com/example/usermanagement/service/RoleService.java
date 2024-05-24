package com.example.usermanagement.service;

import com.example.usermanagement.dto.PrivilegeRequest;
import com.example.usermanagement.dto.RoleRequest;
import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class RoleService {
        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private PrivilegeService privilegeService;

    // Simplified conversion methods
    private Role convertToEntity(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        return role;
    }

    private RoleRequest convertToDto(Role role) {
        return RoleRequest.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    private Set<Privilege> convertToEntities(List<PrivilegeRequest> privilegeRequests) {
        return privilegeRequests.stream()
                .map(privilegeRequest -> new Privilege(privilegeRequest))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private List<PrivilegeRequest> convertToDtos(Set<Privilege> privileges) {
        return privileges.stream()
                .map(privilege -> new PrivilegeRequest(privilege))
                .collect(Collectors.toList());
    }

    // Improved createRoleWithPrivileges method
    public RoleRequest createRoleWithPrivileges(RoleRequest roleRequest) {
        Role role = convertToEntity(roleRequest); // Convert RoleRequest to Role entity
        Set<Privilege> persistedPrivileges = new HashSet<>();

        for (PrivilegeRequest privilegeRequest : roleRequest.getPrivileges()) {
            Privilege persistedPrivilege = privilegeService.findByName(privilegeRequest.getCode());
            if (persistedPrivilege == null) {
                persistedPrivilege = privilegeService.createPrivilege(privilegeRequest); // Create new privilege if not found
            }
            persistedPrivileges.add(persistedPrivilege);
        }

        role.setPrivileges(persistedPrivileges); // Associate privileges with the role
        Role savedRole = roleRepository.save(role); // Save role with associated privileges

        return convertToDto(savedRole); // Convert saved Role entity back to RoleRequest DTO for response
    }
    // Enhanced getRoleById method with more descriptive exception
    public RoleRequest getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
    }

    // Streamlined getAllRoles method
    public List<RoleRequest> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Optimized updateRole method
    public RoleRequest updateRole(Long id, RoleRequest roleDetails, Privilege PrivilegeRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));

        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());

        // Clear existing privileges
        role.setPrivileges(new HashSet<>());

        // Process new privileges
        for (PrivilegeRequest privilegeRequest : roleDetails.getPrivileges()) {
            // Try to find an existing privilege by code
            Privilege existingPrivilege = privilegeService.findByName(privilegeRequest.getCode());
            if (existingPrivilege!= null) {
                // Update the existing privilege
                existingPrivilege.setName(privilegeRequest.getName());
                existingPrivilege.setDescription(privilegeRequest.getDescription());
                // Assuming you have a method to save the updated privilege
                privilegeService.updatePrivilege(existingPrivilege);
            } else {
                // Create a new privilege if it doesn't exist
                PrivilegeRequest newPrivilege = new PrivilegeRequest(PrivilegeRequest );
                privilegeService.createPrivilege(newPrivilege);
            }
        }

        // Save the updated role
        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    // Simplified deleteRole method
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public RoleRequest updateRole(Long id, RoleRequest roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));

        // Step 2: Update the role's details
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());

        // Step 4: Save the updated role
        Role savedRole = roleRepository.save(role);

        // Step 5: Return the updated RoleRequest
        return convertToDto(savedRole);
    }
}
