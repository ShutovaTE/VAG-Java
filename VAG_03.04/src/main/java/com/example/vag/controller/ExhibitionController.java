package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.ExhibitionService;
import com.example.vag.service.UserService;
import com.example.vag.dto.ExhibitionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/exhibition")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;
    private final ArtworkService artworkService;
    private final UserService userService;

    public ExhibitionController(ExhibitionService exhibitionService,
                                ArtworkService artworkService,
                                UserService userService) {
        this.exhibitionService = exhibitionService;
        this.artworkService = artworkService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listExhibitions(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        Page<Exhibition> exhibitionPage = exhibitionService.findPaginatedPublicExhibitions(page, size);
        model.addAttribute("exhibitions", exhibitionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", exhibitionPage.getTotalPages());
        return "exhibition/list";
    }

    @GetMapping("/details/{id}")
    public String viewExhibition(@PathVariable Long id, Model model) {
        Exhibition exhibition = exhibitionService.findById(id).orElseThrow();
        model.addAttribute("exhibition", exhibition);
        model.addAttribute("artworks", artworkService.findByExhibitionId(id));
        return "exhibition/details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("exhibition", new ExhibitionDTO());
        return "exhibition/create";
    }

    @PostMapping("/create")
    public String createExhibition(@Valid @ModelAttribute("exhibition") ExhibitionDTO exhibitionDTO,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "exhibition/create";
        }

        User user = userService.getCurrentUser();
        exhibitionService.create(exhibitionDTO, user);

        return "redirect:/user/profile?created";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Exhibition exhibition = exhibitionService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!exhibition.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        ExhibitionDTO exhibitionDTO = new ExhibitionDTO();
        exhibitionDTO.setId(exhibition.getId());
        exhibitionDTO.setTitle(exhibition.getTitle());
        exhibitionDTO.setDescription(exhibition.getDescription());
        exhibitionDTO.setPrivate(exhibition.isPrivate());

        model.addAttribute("exhibition", exhibitionDTO);
        return "exhibition/edit";
    }

    @PostMapping("/edit")
    public String updateExhibition(@Valid @ModelAttribute("exhibition") ExhibitionDTO exhibitionDTO,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "exhibition/edit";
        }

        exhibitionService.update(exhibitionDTO);

        return "redirect:/user/profile?updated";
    }

    @PostMapping("/delete/{id}")
    public String deleteExhibition(@PathVariable Long id) {
        Exhibition exhibition = exhibitionService.findById(id).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!exhibition.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        exhibitionService.delete(exhibition);
        return "redirect:/user/profile?deleted";
    }

    @PostMapping("/add-artwork/{exhibitionId}/{artworkId}")
    public String addArtworkToExhibition(@PathVariable Long exhibitionId,
                                         @PathVariable Long artworkId) {
        Exhibition exhibition = exhibitionService.findById(exhibitionId).orElseThrow();
        Artwork artwork = artworkService.findById(artworkId).orElseThrow();

        artwork.setExhibition(exhibition);
        artworkService.save(artwork);

        return "redirect:/exhibition/details/" + exhibitionId;
    }

    @PostMapping("/remove-artwork/{exhibitionId}/{artworkId}")
    public String removeArtworkFromExhibition(@PathVariable Long exhibitionId,
                                              @PathVariable Long artworkId) {
        Exhibition exhibition = exhibitionService.findById(exhibitionId).orElseThrow();
        Artwork artwork = artworkService.findById(artworkId).orElseThrow();
        User currentUser = userService.getCurrentUser();

        if (!exhibition.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/auth/access-denied";
        }

        artwork.setExhibition(null);
        artworkService.save(artwork);

        return "redirect:/exhibition/details/" + exhibitionId;
    }
}