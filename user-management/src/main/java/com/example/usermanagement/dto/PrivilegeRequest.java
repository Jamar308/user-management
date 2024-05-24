package com.example.usermanagement.dto;

import com.example.usermanagement.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeRequest {
    private String name;
    private String code;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PrivilegeRequest(Privilege privilege) {
        this.name = privilege.getName();
        this.description = privilege.getDescription();
        this.createdDate = privilege.getCreatedDate();
        this.updatedDate = privilege.getUpdatedDate();
    }

    // Builder pattern to set code with a default value
    public String getCode() {
        return generateCode();
    }

    // Generate a unique code for each PrivilegeRequest
    private String generateCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        return "P-" + randomNumber;
    }
}
