package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import beans.Artwork;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ArtworkService;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Controller
public class ArtworkController {
    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping("/artworks")
    public String getAllArtworks(Model model) {
        Artwork artwork1 = new Artwork();
        artwork1.setId(1);
        artwork1.setTitle("Mona Lisa");
        artwork1.setDescription("A famous painting by Leonardo da Vinci");
        artwork1.setImage("mona_lisa.jpg");
        artwork1.setArtistId(101);
        artwork1.setLikes(10);
        artwork1.setDateCreation(Date.valueOf("1503-01-01"));
        model.addAttribute("categories");
        artwork1.setPublished(true);

        Artwork artwork2 = new Artwork();
        artwork2.setId(2);
        artwork2.setTitle("The Starry Night");
        artwork2.setDescription("A famous painting by Vincent van Gogh");
        artwork2.setImage("starry_night.jpg");
        artwork2.setArtistId(102);
        artwork2.setLikes(20);
        artwork2.setDateCreation(Date.valueOf("1889-06-01"));
        artwork2.setPublished(true);

        List<Artwork> artworks = Arrays.asList(artwork1, artwork2);
        model.addAttribute("artworks", artworks);

        return "artworks";
    }

    @GetMapping("/artworks/detail/{id}")
    public String getArtworkDetail(@PathVariable("id") int id, Model model) {
        Artwork artwork = new Artwork();

        if (id == 1) {
            artwork.setId(1);
            artwork.setTitle("Mona Lisa");
            artwork.setDescription("A masterpiece of Renaissance art.");
            artwork.setImage("mona_lisa.jpg");
            artwork.setArtistId(101);
            artwork.setLikes(10);
            artwork.setViews(100);
            artwork.setDateCreation(Date.valueOf("1503-01-01"));
        } else if (id == 2) {
            artwork.setId(2);
            artwork.setTitle("The Starry Night");
            artwork.setDescription("A famous post-impressionist painting.");
            artwork.setImage("starry_night.jpg");
            artwork.setArtistId(102);
            artwork.setLikes(20);
            artwork.setViews(200);
            artwork.setDateCreation(Date.valueOf("1889-06-01"));
        }


        model.addAttribute("artwork", artwork);
        return "artwork_detail";
    }

    @PostMapping("/artworks/like/{id}")
    @ResponseBody
    public int likeArtwork(@PathVariable("id") int id) {
        Artwork artwork = artworkService.getArtworkById(id);
        artwork.incrementLikes();
        return artwork.getLikes();
    }

    @PostMapping("/artworks/unlike/{id}")
    @ResponseBody
    public int unlikeArtwork(@PathVariable("id") int id) {
        Artwork artwork = artworkService.getArtworkById(id);
        artwork.decrementLikes();
        return artwork.getLikes();
    }

    @GetMapping("/artworks/create")
    public String showCreateArtworkPage() {
        return "artwork_create";
    }

    @PostMapping("/artworks/delete/{id}")
    public String deleteArtwork(@PathVariable("id") int id) {
        System.out.println("Artwork with ID " + id + " has been deleted.");
        return "redirect:/artworks";
    }

}

