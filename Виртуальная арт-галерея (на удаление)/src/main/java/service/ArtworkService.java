package service;

import dao.classes.ArtworkDAOImpl;
import org.springframework.stereotype.Service;
import beans.Artwork;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;


public class ArtworkService {
    private final ArtworkDAOImpl artworkDAO;

    public ArtworkService(ArtworkDAOImpl artworkDAO) {
        this.artworkDAO = artworkDAO;
    }
    public List<Artwork> getAllArtworks() {
        Artwork artwork1 = new Artwork();
        artwork1.setId(1);
        artwork1.setTitle("Mona Lisa");
        artwork1.setDescription("A masterpiece of Renaissance art.");
        artwork1.setDateCreation(Date.valueOf("1503-01-01"));

        Artwork artwork2 = new Artwork();
        artwork2.setId(2);
        artwork2.setTitle("The Starry Night");
        artwork2.setDescription("A famous post-impressionist painting.");

        return Arrays.asList(artwork1, artwork2);
    }

    public Artwork getArtworkById(int id) {
        Artwork artwork = new Artwork();
        artwork.setId(id);
        artwork.setTitle("Static Artwork");
        artwork.setDescription("Description of artwork with ID " + id);
        return artwork;
    }
    public void incrementLikes(int id) {
        Artwork artwork = getArtworkById(id);
        artwork.setLikes(artwork.getLikes() + 1);
    }

    public void decrementLikes(int id) {
        Artwork artwork = getArtworkById(id);
        artwork.setLikes(artwork.getLikes() - 1);
    }

}
