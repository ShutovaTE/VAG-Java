package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.*;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.CategoryService;
import com.example.vag.service.UserService;
import com.example.vag.service.ExhibitionService;
import com.example.vag.util.FileUploadUtil;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
    private final ExhibitionService exhibitionService;
    private final FileUploadUtil fileUploadUtil;

    public ArtworkController(ArtworkService artworkService, CategoryService categoryService, UserService userService, ExhibitionService exhibitionService, FileUploadUtil fileUploadUtil) {
        this.artworkService = artworkService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.exhibitionService = exhibitionService;
        this.fileUploadUtil = fileUploadUtil;
    }

    @GetMapping("/list")
    public String listArtworks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // Ensure page number is non-negative
        page = Math.max(0, page);
        
        Page<Artwork> artworkPage = artworkService.findPaginatedApprovedArtworks(PageRequest.of(page, size));
        model.addAttribute("artworks", artworkPage);
        return "artwork/list";
    }

    @GetMapping("/details/{id}")
    public String viewArtwork(@PathVariable("id") Long id, Model model) {
        Artwork artwork = artworkService.findByIdWithComments(id);
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser(); // пусть вернет null, если неавторизован
        } catch (Exception e) {
            // Не критично — просто null
        }

        boolean isApproved = Artwork.ArtworkStatus.APPROVED.name().equals(artwork.getStatus());
        boolean isAuthorOrAdmin = currentUser != null &&
                (currentUser.getId().equals(artwork.getUser().getId()) || currentUser.hasRole("ROLE_ADMIN"));

        // Доступ запрещен только если не автор и не админ, и работа не одобрена
        if (!isApproved && !isAuthorOrAdmin) {
            return "redirect:/auth/access-denied";
        }

        model.addAttribute("artwork", artwork);
        model.addAttribute("isLiked", currentUser != null && artworkService.isLikedByUser(artwork, currentUser));
        model.addAttribute("isAuthenticated", currentUser != null);

        return "artwork/details";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(required = false) Long exhibitionId, Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryIds", new ArrayList<Long>());
        model.addAttribute("exhibitionId", exhibitionId);
        return "artwork/create";
    }

    @PostMapping("/create")
    @Transactional
    public String createArtwork(
            @Valid @ModelAttribute("artwork") Artwork artwork,
            BindingResult bindingResult,
            @RequestParam("categoryIds") List<Long> categoryIds,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam(required = false) Long exhibitionId,
            Model model) throws IOException {

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("selectedCategoryIds", categoryIds);
            model.addAttribute("exhibitionId", exhibitionId);
            return "artwork/create";
        }

        Artwork savedArtwork = artworkService.create(artwork, imageFile, currentUser);
        
        if (exhibitionId != null) {
            Exhibition exhibition = exhibitionService.findById(exhibitionId).orElseThrow();
            Long currentUserId = currentUser.getId();
            Long exhibitionUserId = exhibition.getUser().getId();
            
            if (!exhibition.isAuthorOnly() || currentUserId.equals(exhibitionUserId)) {
                exhibition.getArtworks().add(savedArtwork);
                savedArtwork.getExhibitions().add(exhibition);
                exhibitionService.save(exhibition);
                artworkService.save(savedArtwork);
                return "redirect:/exhibition/details/" + exhibitionId;
            }
        }
        
        return "redirect:/user/profile?created";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Artwork existingArtwork = artworkService.findByIdWithCategories(id).orElseThrow();
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


    @PostMapping("/edit")
    @Transactional
    public String updateArtwork(
            @Valid @ModelAttribute("artwork") Artwork artwork,
            BindingResult bindingResult,
            @RequestParam("categoryIds") List<Long> categoryIds,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("selectedCategoryIds", categoryIds);
            return "artwork/edit";
        }

        Artwork existingArtwork = artworkService.findByIdWithCategories(artwork.getId()).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!existingArtwork.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        List<Category> categories = categoryService.findAllByIds(categoryIds);
        existingArtwork.setCategories(new HashSet<>(categories));
        existingArtwork.setTitle(artwork.getTitle());
        existingArtwork.setDescription(artwork.getDescription());
        
        existingArtwork.setStatus(Artwork.ArtworkStatus.PENDING.name());

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String safeFileName = fileName
                    .replace(" ", "_")
                    .replaceAll("[^a-zA-Z0-9._-]", "");
            String relativePath = "artwork-images/" + currentUser.getId() + "/" + safeFileName;
            existingArtwork.setImagePath(relativePath);
            fileUploadUtil.saveFile(currentUser.getId(), safeFileName, imageFile);
        }

        artworkService.save(existingArtwork);
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

    @GetMapping("/artworks")
    public String showArtworks(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 12;
        // Ensure page number is non-negative
        page = Math.max(0, page);
        
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Artwork> artworkPage = artworkService.getApprovedArtworks(pageable);
        
        if (page > 0 && artworkPage.getContent().isEmpty()) {
            return "redirect:/artwork/artworks?page=" + (page - 1);
        }
        
        model.addAttribute("artworks", artworkPage);
        return "artwork/list";
    }

}