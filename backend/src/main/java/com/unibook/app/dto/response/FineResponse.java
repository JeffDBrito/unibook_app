package com.unibook.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FineResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "15.50")
    private BigDecimal amount;

    @Schema(example = "Late return")
    private String reason;

    @Schema(example = "2026-05-14")
    private LocalDate issuedDate;

    @Schema(example = "2026-05-16")
    private LocalDate paidDate;

    @Schema(example = "PENDING")
    private String status;

    private LoanResponse loan;
}
