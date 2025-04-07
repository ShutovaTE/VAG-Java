package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ArtworkService artworkService;

    public UserController(UserService userService, ArtworkService artworkService) {
        this.userService = userService;
        this.artworkService = artworkService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("artworks", artworkService.findByUser(user));
        return "user/profile";
    }

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("artworks", artworkService.findByUser(user));
        return "user/profile";
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
                bindingResult.rejectValue("username", "error.user", "Username already exists");
                return "user/settings";
            }
        }

        if (!user.getEmail().equals(currentUser.getEmail())) {
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                bindingResult.rejectValue("email", "error.user", "Email already exists");
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
    public String likedArtworks(Model model) {
        User user = userService.getCurrentUser();
        List<Artwork> likedArtworks = artworkService.findLikedArtworks(user);
        model.addAttribute("artworks", likedArtworks);
        return "user/liked";
    }
}