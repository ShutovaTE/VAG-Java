package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import com.example.vag.service.UserService;
import com.example.vag.dto.ArtworkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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
    public String listArtworks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model) {
        Page<Artwork> artworkPage = artworkService.findPaginatedApprovedArtworks(page, size);
        model.addAttribute("artworks", artworkPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", artworkPage.getTotalPages());
        return "artwork/list";
    }

    @GetMapping("/details/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        model.addAttribute("artwork", artwork);
        model.addAttribute("isLiked", currentUser != null &&
                artworkService.isLikedByUser(artwork, currentUser));
        return "artwork/details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("artwork", new ArtworkDTO());
        model.addAttribute("categories", categoryService.findAll());
        return "artwork/create";
    }

    @PostMapping("/create")
    public String createArtwork(@Valid @ModelAttribute("artwork") ArtworkDTO artworkDTO,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "artwork/create";
        }

        if (imageFile.isEmpty()) {
            bindingResult.rejectValue("imageFile", "error.artwork", "Image is required");
            model.addAttribute("categories", categoryService.findAll());
            return "artwork/create";
        }

        User user = userService.getCurrentUser();
        artworkService.create(artworkDTO, imageFile, user);

        return "redirect:/user/profile?created";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!artwork.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        ArtworkDTO artworkDTO = new ArtworkDTO();
        artworkDTO.setId(artwork.getId());
        artworkDTO.setTitle(artwork.getTitle());
        artworkDTO.setDescription(artwork.getDescription());
        artworkDTO.setCategoryId(artwork.getCategory().getId());

        model.addAttribute("artwork", artworkDTO);
        model.addAttribute("categories", categoryService.findAll());
        return "artwork/edit";
    }

    @PostMapping("/edit")
    public String updateArtwork(@Valid @ModelAttribute("artwork") ArtworkDTO artworkDTO,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "artwork/edit";
        }

        artworkService.update(artworkDTO, imageFile);

        return "redirect:/user/profile?updated";
    }

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