package dao.interfaces;

import beans.User;

import java.util.List;

public interface ArtistDAO {
    void create(User artist);

    void update(User artist);

    void delete(int id);

    User getById(int id);

    List<User> getAll();
}
