package com.unibook.app.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequestResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long userId;

    @Schema(example = "John Doe")
    private String userName;

    @Schema(example = "john_doe")
    private String userLogin;

    @Schema(example = "1")
    private Long bookId;

    @Schema(example = "The Great Gatsby")
    private String bookTitle;
    
    @Schema(example = "978-0-7432-7356-5")
    private String isbn;
    
    @Schema(example = "PENDING")
    private String status;

    @Schema(example = "2026-05-14")
    private LocalDateTime requestedAt;
    
}
