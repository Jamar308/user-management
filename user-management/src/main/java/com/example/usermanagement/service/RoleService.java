package com.example.usermanagement.service;

import com.example.usermanagement.dto.PrivilegeRequest;
import com.example.usermanagement.dto.RoleRequest;
import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.repositories.RoleRepository;
import com.example.usermanagement.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        role.setRoleName(roleRequest.getRoleName());
        role.setRoleDescription(roleRequest.getRoleDescription());
        return role;
    }

    private RoleRequest convertToDto(Role role) {
        return RoleRequest.builder()
                .roleName(role.getRoleName())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    private Set<Privilege> convertToEntities(List<PrivilegeRequest> privilegeRequests) {
        return privilegeRequests.stream()
                .map(privilegeRequest -> new Privilege(privilegeRequest))
                .collect(Collectors.toCollection(HashSet::new));
    }

    public RoleRequest createRoleWithPrivileges(RoleRequest roleRequest) {
        Role role = convertToEntity(roleRequest); // Convert RoleRequest to Role entity
        Set<Privilege> persistedPrivileges = new HashSet<>();

        for (String code : roleRequest.getPrivilegeCode()) {
            Privilege persistedPrivilege = privilegeService.findByName(code);
            if (persistedPrivilege != null) {
                persistedPrivileges.add(persistedPrivilege);
                // persistedPrivilege = privilegeService.createPrivilege(privilegeRequest); // Create new privilege if not found
            }

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
    public ResponseEntity<RoleResponse> updateRole(RoleRequest roleRequest, String success) {

        ResponseEntity<RoleResponse> response = new ResponseEntity<>(success, HttpStatus.OK);

        Optional<Role> role = roleRepository.findById(roleRequest.getId());
        try {
            if (role.isPresent()){
                Set<Privilege> persistedPrivileges = new HashSet<>();

                for (String code : roleRequest.getPrivilegeCode()) {
                    Privilege persistedPrivilege = privilegeService.findByName(code);
                    if (persistedPrivilege != null) {
                        persistedPrivileges.add(persistedPrivilege);
                        // persistedPrivilege = privilegeService.createPrivilege(privilegeRequest); // Create new privilege if not found
                    }

                }
               Role existingRole = role.get();
               existingRole.setRoleDescription(roleRequest.getRoleDescription());
               existingRole.setRoleName(roleRequest.getRoleName());
               existingRole.setPrivileges(persistedPrivileges);
               Role savedRole = roleRepository.save(existingRole);
              RoleResponse entity = RoleResponse.builder()
                               .roleName(savedRole.getRoleName())
                                       .roleDescription(savedRole.getRoleDescription())
                                               .id(savedRole.getRoleId())
                                                       .privilegeCode(savedRole.getPrivileges())
                                                               .build();
               response.setMessage("Role updated successfully");
               response.setStatusCode(HttpStatus.CREATED.value());
               response.setEntity(entity);
            }else {
                response.setMessage("Role not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }


        } catch (Exception e) {
            response.setMessage("An error occurred while updating role");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return response;
    }

    // Simplified deleteRole method
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public RoleRequest updateRole(Long id, RoleRequest roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));

        // Step 2: Update the role's details
        role.setRoleName(roleDetails.getRoleName());
        role.setRoleDescription(roleDetails.getRoleDescription());

        // Step 4: Save the updated role
        Role savedRole = roleRepository.save(role);

        // Step 5: Return the updated RoleRequest
        return convertToDto(savedRole);
    }
}
