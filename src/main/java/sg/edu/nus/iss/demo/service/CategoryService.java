package sg.edu.nus.iss.demo.service;

import java.util.List;

import sg.edu.nus.iss.demo.model.Category;

public interface CategoryService {
    Category saveCategory(Category category);
    
    List<Category> getCategoryList();

    Category updateCategory(Category category, Long categoryId);

    Boolean deleteCategoryById(Long categoryId);
}
