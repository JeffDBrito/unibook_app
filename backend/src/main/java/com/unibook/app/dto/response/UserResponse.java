package com.unibook.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    @Schema(example = "john123")
    private String login;
    
    @Schema(example = "[Admin, Teacher]")
    private String roles;

    private PersonResponse person;
    
}