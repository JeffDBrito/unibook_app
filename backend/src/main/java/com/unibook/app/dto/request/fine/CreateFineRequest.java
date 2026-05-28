package com.unibook.app.dto.request.fine;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFineRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    @Digits(integer = 8, fraction = 2, message = "Amount must have up to 8 integer digits and 2 decimal places")
    private BigDecimal amount;

    @NotBlank(message = "Reason is required")
    @Schema(example = "Late return")
    private String reason;

    @NotNull(message = "Loan is required")
    @Schema(example = "1")
    private Long loanId;
}
