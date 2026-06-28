package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.fine.CreateFineRequest;
import com.unibook.app.dto.response.FineResponse;
import com.unibook.app.service.FineService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    // Create Fine
    @PostMapping
    @Operation(summary = "Create a fine",description = "Creates a new fine.", tags = {"Fine Endpoints"})
    public FineResponse create(@Valid @RequestBody CreateFineRequest request) {
        return fineService.createFine(request);
    }

    // Pay Fine
    @PostMapping("/{id}/pay")
    @Operation(summary = "Pay fine",description = "Marks a fine as paid.", tags = {"Fine Endpoints"})
    public FineResponse pay(@PathVariable Long id) {
        return fineService.payFine(id);
    }

    @GetMapping
    @Operation(summary = "List Fines",description = "Retrieves a list of all Fines and returns their details", tags = {"Fine Endpoints"})
    public List<FineResponse> findAll(){
        return fineService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "List Fines",description = "Retrieves a list of all Fines and returns their details", tags = {"Fine Endpoints"})
    public FineResponse findById(@PathVariable Long id){
        return fineService.findById(id);
    }

}
