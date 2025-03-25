package dao.interfaces;

import beans.Categories;

import java.util.List;

public interface CategoryDAO {
    void create(Categories category);

    void update(Categories category);

    void delete(int id);

    Categories getById(int id);

    List<Categories> getAll();
}
