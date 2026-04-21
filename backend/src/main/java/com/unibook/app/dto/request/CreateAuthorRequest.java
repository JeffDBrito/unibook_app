package com.unibook.app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthorRequest {
    
    private String name;
    private String biography;

    public CreateAuthorRequest() {}

}
