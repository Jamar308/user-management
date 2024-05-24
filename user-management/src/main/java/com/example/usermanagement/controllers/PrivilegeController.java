package com.example.usermanagement.controllers;

import com.example.usermanagement.dto.PrivilegeRequest;
import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("api/privileges")
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping
    public ResponseEntity<Privilege> createPrivilege(@RequestBody PrivilegeRequest privilegeRequest) {
        Privilege privilege = privilegeService.createPrivilege(privilegeRequest);
        return ResponseEntity.ok(privilege);
    }

    @GetMapping
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        return ResponseEntity.ok(privileges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long id) {
        Optional<Privilege> privilege = privilegeService.getPrivilegeById(id);
        return privilege.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Privilege> updatePrivilege(@PathVariable Long id, @RequestBody PrivilegeRequest privilegeRequest) {
        Privilege updatedPrivilege = privilegeService.updatePrivilege(id, privilegeRequest);
        return ResponseEntity.ok(updatedPrivilege);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegeService.deletePrivilege(id);
        return ResponseEntity.noContent().build();
    }
}
