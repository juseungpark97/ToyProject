package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.js.bookforum.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   
}
