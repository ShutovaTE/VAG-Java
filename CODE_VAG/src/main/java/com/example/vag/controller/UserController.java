package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.vag.service.ExhibitionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ArtworkService artworkService;
    private final ExhibitionService exhibitionService;

    public UserController(UserService userService, ArtworkService artworkService, ExhibitionService exhibitionService) {
        this.userService = userService;
        this.artworkService = artworkService;
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("/profile")
    @Transactional(readOnly = true)
    public String showProfile(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        User user = userService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwnProfile = authentication != null && authentication.getName().equals(user.getUsername());

        Pageable pageable = PageRequest.of(page, size);
        Page<Artwork> artworkPage;

        if (isOwnProfile) {
            artworkPage = artworkService.findByUser(user, pageable);
        } else {
            artworkPage = artworkService.findByUserAndStatus(user, "APPROVED", pageable);
        }

        artworkPage.forEach(artwork -> {
            artwork.getCategories().size();
            artwork.getUser().getUsername();
        });

        Page<Exhibition> exhibitionPage = exhibitionService.findByUser(user, pageable);
        exhibitionPage.forEach(exhibition -> {
            exhibition.getUser().getUsername();
            if (!exhibition.getArtworks().isEmpty()) {
                exhibition.getArtworks().forEach(artwork -> {
                    artwork.getStatus();
                });
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("artworks", artworkPage);
        model.addAttribute("exhibitions", exhibitionPage);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "user/profile";
    }

    @GetMapping("/profile/{id}")
    @Transactional(readOnly = true)
    public String showUserProfile(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        User user = userService.findById(id).orElseThrow();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwnProfile = authentication != null && authentication.getName().equals(user.getUsername());

        Pageable pageable = PageRequest.of(page, size);
        Page<Artwork> artworkPage;

        if (isOwnProfile) {
            artworkPage = artworkService.findByUser(user, pageable);
        } else {
            artworkPage = artworkService.findByUserAndStatus(user, "APPROVED", pageable);
        }

        artworkPage.forEach(artwork -> {
            artwork.getCategories().size();
            artwork.getUser().getUsername();
        });

        Page<Exhibition> exhibitionPage = exhibitionService.findByUser(user, pageable);
        exhibitionPage.forEach(exhibition -> {
            exhibition.getUser().getUsername();
            if (!exhibition.getArtworks().isEmpty()) {
                exhibition.getArtworks().forEach(artwork -> {
                    artwork.getStatus();
                });
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("artworks", artworkPage);
        model.addAttribute("exhibitions", exhibitionPage);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("isAuthenticated", authentication != null);
        return "user/profile";
    }

    @GetMapping("/settings")
    public String showSettings(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "user/settings";
    }

    @PostMapping("/settings")
    public String updateSettings(@Valid @ModelAttribute("user") User user,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/settings";
        }

        User currentUser = userService.getCurrentUser();

        if (!user.getUsername().equals(currentUser.getUsername())) {
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                bindingResult.rejectValue("username", "error.user", "Пользователь с таким именем уже существует");
                return "user/settings";
            }
        }

        if (!user.getEmail().equals(currentUser.getEmail())) {
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                bindingResult.rejectValue("email", "error.user", "Пользователь с таким email уже существует");
                return "user/settings";
            }
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.user", "Пароли не совпадают");
                return "user/settings";
            }
        }

        userService.update(user);

        if (!user.getUsername().equals(currentUser.getUsername())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/user/profile?updated";
    }

    @GetMapping("/liked")
    public String likedArtworks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        User user = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        Page<Artwork> artworkPage = artworkService.findLikedArtworks(user, pageable);

        model.addAttribute("artworks", artworkPage);
        return "user/liked";
    }

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public String listUsers(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));
        Page<User> userPage = userService.findAll(pageable);

        userPage.getContent().forEach(user -> {
            user.getArtworks().size();
            user.getExhibitions().size();
        });

        model.addAttribute("users", userPage);
        return "user/list";
    }
}