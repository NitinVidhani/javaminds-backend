package tech.javaminds.javaminds.service;

import tech.javaminds.javaminds.dto.CategoryDto;
import tech.javaminds.javaminds.entity.Category;
import tech.javaminds.javaminds.entity.Post;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);

    Category findCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long id, CategoryDto categoryDto);

    void deleteCategory(long id);

    Category findByName(String name);

    Category getTopCategory();

}
