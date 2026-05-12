package com.unibook.app.dto.request.book;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    
    private String title;
    private String isbn;
    private String description;
    private Integer publicationYear;
    private Long publisherId;
    private List<Long> authorIds;
    private List<Long> categoryIds;

}
