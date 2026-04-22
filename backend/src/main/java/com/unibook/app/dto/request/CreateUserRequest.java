package com.unibook.app.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String name;
    private String email;
    private String login;
    private String password;
    private List<Long> roleIds;
    private boolean superuser;

    public CreateUserRequest() {}

}