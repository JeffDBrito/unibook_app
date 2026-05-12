package com.unibook.app.dto.request.author;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAuthorRequest {
    
    private String name;
    private String biography;

}
