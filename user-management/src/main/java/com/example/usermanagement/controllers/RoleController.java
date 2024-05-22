package com.example.usermanagement.controllers;

import com.example.usermanagement.model.Role;
import com.example.usermanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

        @Autowired
        private RoleService roleService;

        @PostMapping
        public ResponseEntity<Role> createRole(@RequestBody Role role) {
            Role createdRole = roleService.createRoleWithPrivileges(role);
            return ResponseEntity.ok(createdRole);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        }

        @GetMapping
        public ResponseEntity<List<Role>> getAllRoles() {
            List<Role> roles = roleService.getAllRoles();
            return ResponseEntity.ok(roles);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
            Role updatedRole = roleService.updateRole(id, roleDetails);
            return ResponseEntity.ok(updatedRole);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        }
}

