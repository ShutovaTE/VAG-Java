package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.*;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import com.example.vag.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/artwork")
public class ArtworkController {

    private final ArtworkService artworkService;
    private final CategoryService categoryService;
    private final UserService userService;


    public ArtworkController(ArtworkService artworkService, CategoryService categoryService, UserService userService) {
        this.artworkService = artworkService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listArtworks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long category,
            Model model) {

        Page<Artwork> artworkPage;
        Pageable pageable = PageRequest.of(page, size);

        if (category != null) {
            artworkPage = artworkService.findByCategoryId(category, pageable);
        } else {
            artworkPage = artworkService.findPaginatedApprovedArtworks(pageable);
        }

        model.addAttribute("artworks", artworkPage);
        model.addAttribute("categories", categoryService.findAll());
        return "artwork/list";
    }

    @GetMapping("/details/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.findByIdWithComments(id);
        User currentUser = userService.getCurrentUser();

        model.addAttribute("artwork", artwork);
        model.addAttribute("isLiked", currentUser != null &&
                artworkService.isLikedByUser(artwork, currentUser));

        return "artwork/details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryIds", new ArrayList<Long>());
        return "artwork/create";
    }

    @PostMapping("/create")
    public String createArtwork(
            @Valid @ModelAttribute("artwork") Artwork artwork,
            BindingResult bindingResult,
            @RequestParam("categoryIds") List<Long> categoryIds,
            @RequestParam("imageFile") MultipartFile imageFile,

            Model model) throws IOException {

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("selectedCategoryIds", categoryIds);
            return "artwork/create";
        }

        artworkService.create(artwork, imageFile, currentUser);
        return "redirect:/user/profile?created";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Artwork existingArtwork = artworkService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!existingArtwork.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        List<Long> selectedCategoryIds = existingArtwork.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        model.addAttribute("selectedCategoryIds", selectedCategoryIds);
        model.addAttribute("artwork", existingArtwork);
        model.addAttribute("categories", categoryService.findAll());
        return "artwork/edit";
    }


    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser();
        artworkService.addComment(id, user, content);
        return "redirect:/artwork/details/" + id;
    }


//    @PostMapping("/edit")
//    public String updateArtwork(
//            @Valid @ModelAttribute("artwork") Artwork artwork,
//            BindingResult bindingResult,
//            @RequestParam("categories") List<Long> categoryIds,
//            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
//            Model model) throws IOException {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("categories", categoryService.findAll());
//            return "artwork/edit";
//        }
//        List<Category> categories = categoryService.findAllById(categoryIds);
//        artwork.setCategories(new HashSet<>(categories));
//        artworkService.update(artwork, imageFile);
//        return "redirect:/user/profile?updated";
//    }

    @PostMapping("/delete/{id}")
    public String deleteArtwork(@PathVariable Long id) {
        Artwork artwork = artworkService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!artwork.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        artworkService.delete(artwork);
        return "redirect:/user/profile?deleted";
    }

    @PostMapping("/like/{id}")
    public String likeArtwork(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        artworkService.likeArtwork(id, user);
        return "redirect:/artwork/details/" + id;
    }

    @PostMapping("/unlike/{id}")
    public String unlikeArtwork(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        artworkService.unlikeArtwork(id, user);
        return "redirect:/artwork/details/" + id;
    }

}