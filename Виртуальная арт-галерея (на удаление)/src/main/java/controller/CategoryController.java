package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service.CategoryService;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }


    @GetMapping("/categories/{id}")
    public String getCategoryById(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category_detail";
    }
    @GetMapping("/categories/create")
    public String createCategoryPage() {
        return "create_category";
    }

}
