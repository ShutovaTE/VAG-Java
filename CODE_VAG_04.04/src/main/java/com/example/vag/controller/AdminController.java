package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.Category;
import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import com.example.vag.service.ExhibitionService;
import com.example.vag.service.UserService;
import com.example.vag.controller.dto.CategoryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ArtworkService artworkService;
    private final CategoryService categoryService;
    private final ExhibitionService exhibitionService;

    public AdminController(UserService userService, ArtworkService artworkService,
                           CategoryService categoryService, ExhibitionService exhibitionService) {
        this.userService = userService;
        this.artworkService = artworkService;
        this.categoryService = categoryService;
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/artworks")
    public String listArtworks(Model model) {
        List<Artwork> artworks = artworkService.findAll();
        model.addAttribute("artworks", artworks);
        return "admin/artworks";
    }

    @PostMapping("/artworks/approve/{id}")
    public String approveArtwork(@PathVariable Long id) {
        artworkService.approveArtwork(id);
        return "redirect:/admin/artworks?approved";
    }

    @PostMapping("/artworks/reject/{id}")
    public String rejectArtwork(@PathVariable Long id) {
        artworkService.rejectArtwork(id);
        return "redirect:/admin/artworks?rejected";
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @GetMapping("/categories/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/create-category";
    }

    @PostMapping("/categories/create")
    public String createCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/create-category";
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        categoryService.save(category);

        return "redirect:/admin/categories?created";
    }

    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        model.addAttribute("category", categoryDTO);
        return "admin/edit-category";
    }

    @PostMapping("/categories/edit")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit-category";
        }

        categoryService.update(categoryDTO);

        return "redirect:/admin/categories?updated";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id).orElseThrow();
        categoryService.delete(category);
        return "redirect:/admin/categories?deleted";
    }

    @GetMapping("/exhibitions")
    public String listExhibitions(Model model) {
        List<Exhibition> exhibitions = exhibitionService.findAll();
        model.addAttribute("exhibitions", exhibitions);
        return "admin/exhibitions";
    }
}