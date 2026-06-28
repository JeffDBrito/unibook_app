package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.category.CreateCategoryRequest;
import com.unibook.app.dto.request.category.PartialUpdateCategoryRequest;
import com.unibook.app.dto.request.category.UpdateCategoryRequest;
import com.unibook.app.dto.response.CategoryResponse;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.CategoryMapper;
import com.unibook.app.model.Category;
import com.unibook.app.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Category
     * @param title
     * @param description
     * @return CategoryResponse
     */
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    /**
     * Delete Category by id
     * @param id
     */
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));   
        category.softDelete();     
        categoryRepository.save(category);
    }

    /**
     * Restore Category by id
     * @param id
     * @return
     */
    public CategoryResponse restoreById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));   
        category.restore();     
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    /**
     * Update Category
     * @param id
     * @param request
     * @param partial
     * @return CategoryResponse
     */
    public CategoryResponse update(Long id, PartialUpdateCategoryRequest request, boolean partial){
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if(!partial || request.getTitle() != null){
            category.setTitle(request.getTitle());
        }

        if(!partial || request.getDescription() != null){
            category.setDescription(request.getDescription());
        }

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, UpdateCategoryRequest request){
        return update(id, CategoryMapper.toPartialUpdate(request), false);
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Categories
     * @return List<CategoryResponse>
     */
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toResponse).toList();
    }

    /**
     * Find Category by id
     * @param id
     * @return CategoryResponse
     */
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return CategoryMapper.toResponse(category);
    }

    /**
     * Find Category title
     * @param title
     * @return CategoryResponse
     */
    public CategoryResponse findByTitle(String title) {
        Category category = categoryRepository.findByTitle(title)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with title: " + title));
        return CategoryMapper.toResponse(category);
    }

}
