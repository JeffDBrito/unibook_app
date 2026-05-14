package com.unibook.app.dto.request.copy;

import com.unibook.app.enums.CopyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "123AS-ASD123")
    private String code;

    @Schema(example = "AVAILABLE")
    private CopyStatus status;

    @Schema(example = "1")
    private Long bookId;

}
