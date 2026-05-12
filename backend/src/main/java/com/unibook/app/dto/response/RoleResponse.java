package com.unibook.app.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
    
    private String name;
    private List<String> permissions;

}
