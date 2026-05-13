package com.unibook.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "admin")
    private String login;
    
    @Schema(example = "admin")
    private String password;

}
