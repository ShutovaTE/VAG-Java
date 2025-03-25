package dao.interfaces;

import beans.Artworks;
import beans.Categories;

import java.sql.SQLException;
import java.util.List;

public interface ArtworkDAO {
    void create(Artworks artwork);

    void update(Artworks artwork);

    void delete(int id);

    Artworks getById(int id);

    List<Artworks> getAll();

    Categories getCategoryForArtwork(int artworkId) throws SQLException;
}
