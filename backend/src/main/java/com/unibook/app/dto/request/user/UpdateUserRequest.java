package com.unibook.app.dto.request.user;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String name;
    private String email;
    private String login;
    private String password;
    private List<Long> roleIds;
    
}
