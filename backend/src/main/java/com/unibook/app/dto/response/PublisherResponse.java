package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Penguin Random House")
    private String title;
    
    @Schema(example = "Penguin Random House is a global book publishing company that publishes a wide range of fiction and non-fiction books across various genres.")
    private String description;

}
