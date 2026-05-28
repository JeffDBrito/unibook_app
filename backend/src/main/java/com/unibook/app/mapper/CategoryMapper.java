package com.unibook.app.mapper;

import com.unibook.app.dto.request.category.PartialUpdateCategoryRequest;
import com.unibook.app.dto.request.category.UpdateCategoryRequest;
import com.unibook.app.dto.response.CategoryResponse;
import com.unibook.app.model.Category;

public class CategoryMapper {
    
    private CategoryMapper(){}

    /**
     * Convert Category instance to CategoryResponse dto
     * @param category
     * @return CategoryResponse
     */
    public static CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setTitle(category.getTitle());
        response.setDescription(category.getDescription());
        return response;
    }

    /**
     * Convert Full update to Partial update
     * @param request
     * @return PartialUpdateCategoryRequest
     */
    public static PartialUpdateCategoryRequest toPartialUpdate(UpdateCategoryRequest request){
        PartialUpdateCategoryRequest partial = new PartialUpdateCategoryRequest();
        partial.setTitle(request.getTitle());
        partial.setDescription(request.getDescription());
        return partial;
    }

}
