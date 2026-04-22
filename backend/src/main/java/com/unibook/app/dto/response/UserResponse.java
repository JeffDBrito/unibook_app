package com.unibook.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String login;

    private String name;
    private String email;

    private String roles;
    private boolean superuser;
}