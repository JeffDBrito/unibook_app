package com.unibook.app.dto.request.fine;

import java.time.LocalDate;

import com.unibook.app.enums.FineStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFineRequest {

    @Schema(example = "PAID")
    private FineStatus status;

    @Schema(example = "2026-05-14")
    private LocalDate paidDate;
}
