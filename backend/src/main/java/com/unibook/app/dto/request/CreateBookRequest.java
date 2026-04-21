package com.unibook.app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
    
    private String title;
    private String isbn;
    private Integer publicationYear;

    public CreateBookRequest() {}

}
