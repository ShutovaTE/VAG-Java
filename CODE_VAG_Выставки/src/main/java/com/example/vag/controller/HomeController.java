package com.example.vag.controller;

import com.example.vag.model.Artwork;
import com.example.vag.model.Exhibition;
import com.example.vag.model.User;
import com.example.vag.service.ArtworkService;
import com.example.vag.service.ExhibitionService;
import com.example.vag.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ArtworkService artworkService;
    private final ExhibitionService exhibitionService;
    private final UserService userService;

    public HomeController(ArtworkService artworkService, ExhibitionService exhibitionService, UserService userService) {
        this.artworkService = artworkService;
        this.exhibitionService = exhibitionService;
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    @Transactional(readOnly = true)
    public String home(Authentication authentication, Model model) {
        // Получаем случайные одобренные работы
        List<Artwork> artworks = artworkService.findByStatus("APPROVED");
        Collections.shuffle(artworks);
        List<Artwork> randomArtworks = artworks.subList(0, Math.min(4, artworks.size()));
        randomArtworks.forEach(artwork -> {
            artwork.getUser().getUsername(); // Инициализируем связанные данные
            artwork.getCategories().size(); // Инициализируем категории
        });
        model.addAttribute("randomArtworks", randomArtworks);

        // Получаем случайные выставки
        List<Exhibition> exhibitions = exhibitionService.findAll();
        Collections.shuffle(exhibitions);
        List<Exhibition> randomExhibitions = exhibitions.subList(0, Math.min(4, exhibitions.size()));
        randomExhibitions.forEach(exhibition -> {
            exhibition.getUser().getUsername(); // Инициализируем связанные данные
            if (!exhibition.getArtworks().isEmpty()) {
                exhibition.getArtworks().size(); // Инициализируем коллекцию работ
                exhibition.getArtworks().forEach(artwork -> {
                    artwork.getStatus(); // Инициализируем статус работы
                });
            }
        });
        model.addAttribute("randomExhibitions", randomExhibitions);

        // Получаем случайных художников с предварительной загрузкой их работ
        List<User> artists = userService.findAll();
        Collections.shuffle(artists);
        List<User> randomArtists = artists.subList(0, Math.min(4, artists.size()));
        randomArtists.forEach(artist -> {
            artist.getArtworks().size(); // Инициализируем коллекцию работ
            artist.getExhibitions().size(); // Инициализируем коллекцию выставок
        });
        model.addAttribute("randomArtists", randomArtists);

        return "index";
    }
}