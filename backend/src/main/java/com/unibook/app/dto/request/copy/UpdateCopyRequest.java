package com.unibook.app.dto.request.copy;

import com.unibook.app.enums.CopyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCopyRequest {    
    
    @Schema(example = "1")
    private Long id;

    @Schema(example = "123AS-ASD123")
    private String code;

    @Schema(example = "AVAILABLE")
    private CopyStatus status;

    @Schema(example = "1")
    private Long inventoryId;

}
