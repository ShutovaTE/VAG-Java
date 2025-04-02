package com.example.vag.controller;

import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.UserService;
import com.example.vag.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/settings")
    public String showSettingsForm(Model model) {
        User user = userService.getCurrentUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        model.addAttribute("user", userDTO);
        return "user/settings";
    }

    @PostMapping("/settings")
    public String updateSettings(@Valid @ModelAttribute("user") UserDTO userDTO,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/settings";
        }

        User currentUser = userService.getCurrentUser();

        if (!userDTO.getUsername().equals(currentUser.getUsername())) {
            if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
                bindingResult.rejectValue("username", "error.user", "Username already exists");
                return "user/settings";
            }
        }

        if (!userDTO.getEmail().equals(currentUser.getEmail())) {
            if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
                bindingResult.rejectValue("email", "error.user", "Email already exists");
                return "user/settings";
            }
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
                return "user/settings";
            }
        }

        userService.update(userDTO);

        // Re-authenticate user if username or password changed
        if (!userDTO.getUsername().equals(currentUser.getUsername())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/user/profile?updated";
    }

    @GetMapping("/liked")
    public String showLikedArtworks(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("artworks", artworkService.findLikedArtworks(user));
        return "user/liked";
    }
}