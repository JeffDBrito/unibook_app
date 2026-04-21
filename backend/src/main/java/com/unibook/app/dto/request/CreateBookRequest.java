package com.unibook.app.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
    
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String description;
    private Long publisherId;
    private List<Long> authorIds;
    // private List<Long> categoryIds;

    public CreateBookRequest() {}

}
