package com.unibook.app.dto.request.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {
    
    private String title;
    private String description;

}
