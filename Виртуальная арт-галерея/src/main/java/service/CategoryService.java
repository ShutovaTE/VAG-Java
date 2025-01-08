package service;

import beans.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private static final List<Category> categories = new ArrayList<>();

    static {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Painting");
        category1.setDescription("Various types of paintings");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Sculpture");
        category2.setDescription("Different types of sculptures");

        categories.add(category1);
        categories.add(category2);
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
