package com.example.usermanagement.model;

import com.example.usermanagement.dto.PrivilegeRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privilegeId;

    private String name;
    private String code;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference // Use this to break the bidirectional relationship for JSON serialization
    private Role role;

    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();



    public Privilege(PrivilegeRequest privilegeRequest) {
        this.name = privilegeRequest.getName();
        this.code = privilegeRequest.getCode();
        this.description = privilegeRequest.getDescription();
        this.createdDate = privilegeRequest.getCreatedDate();
        this.updatedDate = LocalDateTime.now();
    }


}
