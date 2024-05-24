package com.example.usermanagement.controllers;

import com.example.usermanagement.dto.ErrorResponseDto;
import com.example.usermanagement.dto.PrivilegeRequest;
import com.example.usermanagement.model.Privilege;
import com.example.usermanagement.service.PrivilegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("api/privileges")
@CrossOrigin("*")
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @Operation(
            summary = "Create privilege REST API",
            description = "REST API to create new privilege"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @PostMapping
    @Valid
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
