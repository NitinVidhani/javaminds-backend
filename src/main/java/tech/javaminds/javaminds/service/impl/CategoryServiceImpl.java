package tech.javaminds.javaminds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.javaminds.javaminds.dto.CategoryDto;
import tech.javaminds.javaminds.entity.Category;
import tech.javaminds.javaminds.entity.Post;
import tech.javaminds.javaminds.exception.ResourceAlreadyExistsException;
import tech.javaminds.javaminds.exception.ResourceNotFoundException;
import tech.javaminds.javaminds.repository.CategoryRepository;
import tech.javaminds.javaminds.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Creates a new category
    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category existedCategory = categoryRepository.findCategoryByName(categoryDto.getName());
        if(existedCategory != null) {
            throw new ResourceAlreadyExistsException("Category with name: " + existedCategory.getName() + " already exists");
        }
        Category category = categoryDto.getCategoryFromDto();
        return categoryRepository.save(category);
    }

    // Retrieves the category using its id
    @Override
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    // Retrieves the list of all the categories
    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategories = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(allCategories::add);
        return allCategories;
    }


    // Updates the category if present (using id)
    @Override
    public Category updateCategory(long id, CategoryDto categoryDto) {
        if (findCategoryById(id) != null) {
            Category category = categoryDto.getCategoryFromDto();
            category.setId(id);
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.delete(findCategoryById(id));
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public Category getTopCategory() {
        return categoryRepository.findTopByOrderByIdAsc();
    }

}

