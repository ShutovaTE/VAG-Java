package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.Category;
import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import com.example.vag.service.ExhibitionService;
import com.example.vag.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listArtworks(
            @RequestParam(required = false) String status,
            Model model
    ) {
        List<Artwork> artworks = status != null && !status.isEmpty()
                ? artworkService.findByStatus(status.toUpperCase())
                : artworkService.findAll();

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

        categories.forEach(category ->
                category.setApprovedArtworksCount(
                        artworkService.countApprovedArtworksByCategoryId(category.getId())
                ));

        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @GetMapping("/categories/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/create-category";
    }


    @PostMapping("/categories/create")
    public String createCategories(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.save(category);
        redirectAttributes.addAttribute("created", true);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        model.addAttribute("category", category);
        return "admin/edit-category";
    }

    @PostMapping("/categories/edit/{id}")
    public String updateCategory(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("category") Category category,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "admin/edit-category";
        }
        categoryService.update(id, category);
        redirectAttributes.addFlashAttribute("success", "Category updated");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id).orElseThrow();
        categoryService.delete(category);
        redirectAttributes.addAttribute("deleted", true);
        return "redirect:/admin/categories";
    }


    @GetMapping("/exhibitions")
    public String listExhibitions(Model model) {
        List<Exhibition> exhibitions = exhibitionService.findAll();
        model.addAttribute("exhibitions", exhibitions);
        return "admin/exhibitions";
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser();
        artworkService.addComment(id, user, content);
        return "redirect:/artwork/details/" + id;
    }
}