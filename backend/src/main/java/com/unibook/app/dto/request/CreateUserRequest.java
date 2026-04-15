package com.unibook.app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String name;
    private String email;
    private String login;
    private String password;
    private Long roleId;
    private boolean superuser;

    public CreateUserRequest() {}

}