package com.unibook.app.dto.request.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {

    @NotNull(message = "User is required")
    @Schema(example = "1")
    private Long userId;

    @NotNull(message = "Copy is required")
    @Schema(example = "1")
    private Long copyId;

    @Future(message = "Due date must be in the future")
    @NotNull(message = "Due date is required")
    @Schema(example = "2026-05-20")
    private LocalDate dueDate;
}
