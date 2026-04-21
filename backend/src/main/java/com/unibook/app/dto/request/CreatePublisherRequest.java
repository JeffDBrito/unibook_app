package com.unibook.app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePublisherRequest {
    
    private String title;
    private String description;

    public CreatePublisherRequest() {}

}
