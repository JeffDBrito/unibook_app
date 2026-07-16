package com.unibook.app.dto.request.loan;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLoanRequest {

    @NotNull(message = "Book is required")
    private Long bookId;
}
