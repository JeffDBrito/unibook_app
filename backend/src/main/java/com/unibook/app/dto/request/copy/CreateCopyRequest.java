package com.unibook.app.dto.request.copy;

import com.unibook.app.enums.CopyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCopyRequest {    

    @Size(min = 12, max = 12, message = "Code must contain 12 characters")
    @NotBlank(message = "Code is required")
    @Schema(example = "123AS-ASD123")
    private String code;

    @NotNull(message = "Status is required")
    @Schema(example = "AVAILABLE")
    private CopyStatus status;

    @NotBlank(message = "Book is required")
    @Schema(example = "1")
    private Long bookId;

}
