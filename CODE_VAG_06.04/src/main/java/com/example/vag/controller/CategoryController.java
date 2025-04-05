package com.example.vag.controller;

import com.example.vag.model.Category;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ArtworkService artworkService;

    public CategoryController(CategoryService categoryService, ArtworkService artworkService) {
        this.categoryService = categoryService;
        this.artworkService = artworkService;
    }

    @GetMapping("/list")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/details/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        model.addAttribute("category", category);
        model.addAttribute("artworks", artworkService.findByCategoryId(id));
        return "category/details";
    }
}