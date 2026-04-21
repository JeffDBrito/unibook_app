package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.CategoryResponse;
import com.unibook.app.model.Category;
import com.unibook.app.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(String title, String description) {
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        Category savedCategory = categoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return toResponse(category);
    }

    public CategoryResponse findByTitle(String title) {
        Category category = categoryRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Category not found with title: " + title));
        return toResponse(category);
    }

    private CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setTitle(category.getTitle());
        response.setDescription(category.getDescription());
        return response;
    }

}
