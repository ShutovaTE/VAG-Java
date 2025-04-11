package com.example.vag.service.impl;

import com.example.vag.model.Category;
import com.example.vag.repository.CategoryRepository;
import com.example.vag.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAllById(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAllWithArtworks();
    }

        @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    @Transactional
    public Category update(Long id, @Valid Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(category);
    }
}