package controller;

import beans.Artist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ArtistProfileController {

    @GetMapping("/artistProfile")
    public String showArtistProfile(Model model) {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setUsername("Zlangel");
        artist.setEmail("zlangek@example.com");
        artist.setPassword("hidden");

        List<String> publications = Arrays.asList(
                "Mona Lisa",
                "The Starry Night"
        );

        List<String> exhibitions = Arrays.asList(
                "Art Critique - Da Vinci and Michelangelo",
                "The Guardian - Top Renaissance Artists"
        );

        model.addAttribute("artist", artist);
        model.addAttribute("publications", publications);
        model.addAttribute("exhibitions", exhibitions);

        return "artistProfile";
    }
}
