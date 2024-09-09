package com.js.bookforum.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.js.bookforum.entity.Category;
import com.js.bookforum.entity.Role;
import com.js.bookforum.repository.CategoryRepository;
import com.js.bookforum.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RoleRepository roleRepository, CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 역할 초기화
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = Role.builder().name("USER").description("Standard user role").build();
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = Role.builder().name("ADMIN").description("Administrator role").build();
            roleRepository.save(adminRole);
        }

        // 카테고리 초기화
        if (categoryRepository.count() == 0) { // 카테고리가 없을 경우에만 추가
            List<Category> categories = List.of(
                Category.builder().name("스릴러").description("스릴러 책").build(),
                Category.builder().name("판타지").description("판타지 책").build(),
                Category.builder().name("과학").description("과학 책").build(),
                Category.builder().name("역사").description("역사 책").build()
            );
            categoryRepository.saveAll(categories);
        }
    }
}