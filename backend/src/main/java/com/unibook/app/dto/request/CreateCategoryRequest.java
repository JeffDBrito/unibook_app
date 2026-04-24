package com.unibook.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {
    
    @Schema(example = "Fiction")
    private String title;

    @Schema(example = "Fiction is a literary genre that includes imaginative or invented stories, often featuring characters and events that are not based on real life.")
    private String description;

    public CreateCategoryRequest() {}

}
