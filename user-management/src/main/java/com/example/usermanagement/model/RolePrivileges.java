//package com.example.usermanagement.model;
//
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class RolePrivileges {
//    @EmbeddedId
//    private RolePrivilegeId id;
//
//    @ManyToOne
//    @MapsId("roleId")
//    private Role role;
//
//    @ManyToOne
//    @MapsId("privilegeId")
//    private Privilege privilege;
//}
