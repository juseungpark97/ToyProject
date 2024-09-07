package com.js.bookforum.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.js.bookforum.entity.Role;
import com.js.bookforum.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 역할이 이미 존재하는지 확인
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = Role.builder()
                                .name("USER")
                                .description("Standard user role")
                                .build();
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = Role.builder()
                                 .name("ADMIN")
                                 .description("Administrator role")
                                 .build();
            roleRepository.save(adminRole);
        }
    }
}