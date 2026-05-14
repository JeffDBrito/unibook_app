package com.unibook.app.dto.request.fine;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFineRequest {

    @Schema(example = "15.50")
    private BigDecimal amount;

    @Schema(example = "Late return")
    private String reason;

    @Schema(example = "1")
    private Long loanId;
}
