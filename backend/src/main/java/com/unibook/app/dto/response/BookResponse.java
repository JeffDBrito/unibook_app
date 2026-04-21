package com.unibook.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;

}
