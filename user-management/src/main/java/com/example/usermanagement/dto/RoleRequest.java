package com.example.usermanagement.dto;

import com.example.usermanagement.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private Long id;
    private String roleName;
    private String roleDescription;
    private Set<String> privilegeCode;

    public RoleRequest(RoleRequest roleRequest) {
    }


//    public List<PrivilegeRequest> getPrivileges() {
//        return getPrivileges();
//    }

   /* public void setPrivileges(Set<Privilege> privileges) {
    }
*/
/*    public PrivilegeRequest[] getPrivileges() {
        return new PrivilegeRequest[0];
    }*/
}
