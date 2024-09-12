package com.js.bookforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.js.bookforum.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
   
}
