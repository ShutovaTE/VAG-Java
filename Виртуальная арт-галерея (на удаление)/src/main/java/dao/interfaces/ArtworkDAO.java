package dao.interfaces;

import beans.Artwork;
import beans.Category;

import java.sql.SQLException;
import java.util.List;

public interface ArtworkDAO {
    void create(Artwork artwork);

    void update(Artwork artwork);

    void delete(int id);

    Artwork getById(int id);

    List<Artwork> getAll();

    Category getCategoryForArtwork(int artworkId) throws SQLException;
}
