package com.example.usermanagement.repositories;

import com.example.usermanagement.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    Privilege findByCode(String code);
}
