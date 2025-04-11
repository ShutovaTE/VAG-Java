package com.example.vag.service;

import com.example.vag.model.Category;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);

    List<Category> findAllById(List<Long> ids);

    void delete(Category category);
    Category update(Category category);
    void deleteById(Long id);
    Category update(Long id, @Valid Category category);

}