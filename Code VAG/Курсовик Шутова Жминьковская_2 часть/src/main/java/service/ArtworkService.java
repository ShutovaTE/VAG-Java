package service;

import dao.classes.ArtworkDAOImpl;
import beans.Artworks;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;


public class ArtworkService {
    private final ArtworkDAOImpl artworkDAO;

    public ArtworkService(ArtworkDAOImpl artworkDAO) {
        this.artworkDAO = artworkDAO;
    }
    public List<Artworks> getAllArtworks() {
        Artworks artwork1 = new Artworks();
        artwork1.setId(1);
        artwork1.setTitle("Mona Lisa");
        artwork1.setDescription("A masterpiece of Renaissance art.");
        artwork1.setDateCreation(Date.valueOf("1503-01-01"));

        Artworks artwork2 = new Artworks();
        artwork2.setId(2);
        artwork2.setTitle("The Starry Night");
        artwork2.setDescription("A famous post-impressionist painting.");

        return Arrays.asList(artwork1, artwork2);
    }

    public Artworks getArtworkById(int id) {
        Artworks artwork = new Artworks();
        artwork.setId(id);
        artwork.setTitle("Static Artwork");
        artwork.setDescription("Description of artwork with ID " + id);
        return artwork;
    }
    public void incrementLikes(int id) {
        Artworks artwork = getArtworkById(id);
        artwork.setLikes(artwork.getLikes() + 1);
    }

    public void decrementLikes(int id) {
        Artworks artwork = getArtworkById(id);
        artwork.setLikes(artwork.getLikes() - 1);
    }

}
