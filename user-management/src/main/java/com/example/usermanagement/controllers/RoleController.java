package com.example.usermanagement.controllers;

import com.example.usermanagement.dto.ErrorResponseDto;
import com.example.usermanagement.dto.RoleRequest;
import com.example.usermanagement.service.RoleResponse;
import com.example.usermanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {

        @Autowired
        private RoleService roleService;


    @Operation(
            summary = "Create Role REST API",
            description = "REST API to create new Role"
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

    @PutMapping("/updateRole")
    public com.example.usermanagement.util.ResponseEntity<RoleResponse> updateRole(@RequestBody RoleRequest roleRequest, String success) {
        return   roleService.updateRole(roleRequest, success);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}

