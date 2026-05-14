package com.unibook.app.dto.response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanResponse {
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "2026-05-13")
    private LocalDate loanDate;

    @Schema(example = "2026-05-20")
    private LocalDate dueDate;

    @Schema(example = "2026-05-18")
    private LocalDate returnDate;

    @Schema(example = "ACTIVE")
    private String status;
    
    private UserResponse user;

    private CopyResponse copy;

}
