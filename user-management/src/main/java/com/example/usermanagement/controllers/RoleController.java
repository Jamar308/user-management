package com.example.usermanagement.controllers;

import com.example.usermanagement.dto.RoleRequest;
import com.example.usermanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

        @Autowired
        private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleRequest> createRole(@RequestBody RoleRequest roleRequest) {
        RoleRequest createdRoleRequest = roleService.createRoleWithPrivileges(roleRequest);
        return ResponseEntity.ok(createdRoleRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleRequest> getRoleById(@PathVariable Long id) {
        RoleRequest roleRequest = roleService.getRoleById(id);
        return ResponseEntity.ok(roleRequest);
    }

    @GetMapping
    public ResponseEntity<List<RoleRequest>> getAllRoles() {
        List<RoleRequest> roles = roleService.getAllRoles().stream()
                .map(RoleRequest::new) // Assuming RoleRequest has a constructor or builder that matches Role's fields
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleRequest> updateRole(@PathVariable Long id, @RequestBody RoleRequest roleDetails) {
        RoleRequest updatedRoleRequest = roleService.updateRole(id, roleDetails);
        return ResponseEntity.ok(updatedRoleRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}

