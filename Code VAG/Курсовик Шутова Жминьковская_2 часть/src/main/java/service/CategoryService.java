package service;

import beans.Categories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private static final List<Categories> categories = new ArrayList<>();

    static {
        Categories category1 = new Categories();
        category1.setId(1);
        category1.setName("Painting");
        category1.setDescription("Various types of paintings");

        Categories category2 = new Categories();
        category2.setId(2);
        category2.setName("Sculpture");
        category2.setDescription("Different types of sculptures");

        categories.add(category1);
        categories.add(category2);
    }

    public List<Categories> getAllCategories() {
        return categories;
    }

    public Categories getCategoryById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
