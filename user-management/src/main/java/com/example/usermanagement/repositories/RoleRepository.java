package com.example.usermanagement.repositories;

import com.example.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role ,Long> {
}
