package com.unibook.app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.fine.CreateFineRequest;
import com.unibook.app.dto.response.FineResponse;
import com.unibook.app.service.FineService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @PostMapping
    @Operation(summary = "Create a fine",description = "Creates a new fine.", tags = {"Fine Endpoints"})
    public FineResponse create(@RequestBody CreateFineRequest request) {
        return fineService.createFine(request);
    }

    @PostMapping("/{id}/pay")
    @Operation(summary = "Pay fine",description = "Marks a fine as paid.", tags = {"Fine Endpoints"})
    public FineResponse pay(@PathVariable Long id) {
        return fineService.payFine(id);
    }

}
