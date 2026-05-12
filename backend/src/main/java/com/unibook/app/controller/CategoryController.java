package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import com.unibook.app.dto.request.category.CreateCategoryRequest;
import com.unibook.app.dto.request.category.UpdateCategoryRequest;
import com.unibook.app.dto.response.CategoryResponse;
import com.unibook.app.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    // List categories
    @GetMapping
    @Operation(summary = "List categories", description = "Retrieves a list of all categories and returns their details.", tags = {"Category Endpoints"})
    public List<CategoryResponse> getAllCategories() {
        return categoryService.findAll();
    }

    // Create category
    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates a new category with the provided details and returns the created category.", tags = {"Category Endpoints"})
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(
            request.getTitle(), 
            request.getDescription()
        );
    }

    // Delete category by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id", description = "Deletes a category by their id.", tags = {"Category Endpoints"})
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    // Get category by id
    @GetMapping("/{id}")
    @Operation(summary = "Get category by id", description = "Retrieves a category by their id and returns the category details.", tags = {"Category Endpoints"})
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    // Get category by title
    @GetMapping("/title/{title}")
    @Operation(summary = "Get category by title", description = "Retrieves a category by their title and returns the category details.", tags = {"Category Endpoints"})
    public CategoryResponse getCategoryByTitle(@PathVariable String title) {
        return categoryService.findByTitle(title);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update Category", description = "Partially updates an existing Category with the provided details and returns the updated Category.", tags = {"Category Endpoints"})
    public CategoryResponse partialUpdate(@PathVariable Long id, @RequestBody UpdateCategoryRequest request){
        return categoryService.update(id, request, true);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Category", description = "Updates an existing Category with the provided details and returns the updated Category.", tags = {"Category Endpoints"})
    public CategoryResponse fullUpdate(@PathVariable Long id, @RequestBody UpdateCategoryRequest request){
        return categoryService.update(id, request, false);
    }

}
