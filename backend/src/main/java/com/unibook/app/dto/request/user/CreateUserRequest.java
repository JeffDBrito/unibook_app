package com.unibook.app.dto.request.user;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "john@email.com")
    private String email;

    @Schema(example = "john123")
    private String login;

    @Schema(example = "123456")
    private String password;

    @Schema(example = "[1, 2]")
    private List<Long> roleIds;

}