package com.example.vag.service;

import com.example.vag.model.Category;
import com.example.vag.controller.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void delete(Category category);
    Category update(CategoryDTO categoryDTO);
}