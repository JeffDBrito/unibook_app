package com.unibook.app.dto.request.book;

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
    private java.util.List<Long> authorIds;
    private java.util.List<Long> categoryIds;

}
