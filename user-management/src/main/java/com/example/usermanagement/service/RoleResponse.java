package com.example.usermanagement.service;

import com.example.usermanagement.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String roleName;
    private String roleDescription;
    private Set<Privilege> privilegeCode;
}
