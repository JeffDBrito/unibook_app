package com.unibook.app.dto.request.publisher;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePublisherRequest {
    
    @Schema(example = "Penguin Random House")
    private String title;
    
    @Schema(example = "Penguin Random House is a global book publishing company that publishes a wide range of fiction and non-fiction books across various genres.")
    private String description;

    public CreatePublisherRequest() {}

}
