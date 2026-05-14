package com.unibook.app.dto.request.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {

    @Schema(example = "1")
    private Long userId;

    @Schema(example = "1")
    private Long copyId;

    @Schema(example = "2026-05-20")
    private LocalDate dueDate;
}
