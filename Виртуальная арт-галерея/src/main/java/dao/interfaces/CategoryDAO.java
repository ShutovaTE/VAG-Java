package dao.interfaces;

import beans.Category;

import java.util.List;

public interface CategoryDAO {
    void create(Category category);

    void update(Category category);

    void delete(int id);

    Category getById(int id);

    List<Category> getAll();
}
