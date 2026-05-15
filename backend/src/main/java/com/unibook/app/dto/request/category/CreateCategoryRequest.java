package com.unibook.app.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequest {
    
    @Schema(example = "Fiction")
    private String title;

    @Schema(example = "Fiction is a literary genre that includes imaginative or invented stories, often featuring characters and events that are not based on real life.")
    private String description;

}
