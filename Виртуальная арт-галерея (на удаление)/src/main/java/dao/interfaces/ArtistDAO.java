package dao.interfaces;

import beans.Artist;

import java.util.List;

public interface ArtistDAO {
    void create(Artist artist);

    void update(Artist artist);

    void delete(int id);

    Artist getById(int id);

    List<Artist> getAll();
}
