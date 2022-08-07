package tech.javaminds.javaminds.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.javaminds.javaminds.dto.CategoryDto;
import tech.javaminds.javaminds.dto.PostDto;
import tech.javaminds.javaminds.entity.Category;
import tech.javaminds.javaminds.entity.Post;
import tech.javaminds.javaminds.service.CategoryService;
import tech.javaminds.javaminds.service.PostService;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") long id) {
        Category foundCategory = categoryService.findCategoryById(id);
        if(foundCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/top")
    public Category getTopCategory() {
        return categoryService.getTopCategory();
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category created = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") long id, @RequestBody CategoryDto categoryDto) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDto);
        if(updatedCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable("categoryId") long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    /*--------------- Posts -----------------*/
    @GetMapping("/{categoryId}/posts")
    public List<Post> getPosts(@PathVariable("categoryId") long id) {
        return postService.getPostsByCategoryId(id);
    }

    @PostMapping("/{categoryId}/posts")
    public Post addPost(@PathVariable("categoryId") long id, @RequestBody PostDto postDto) {
        logger.debug(postDto.getTitle());
        return postService.createPost(id, postDto);
    }

}
