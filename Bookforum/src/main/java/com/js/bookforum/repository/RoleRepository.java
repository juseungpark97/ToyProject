package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.js.bookforum.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}