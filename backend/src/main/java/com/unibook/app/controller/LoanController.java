package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.loan.CreateLoanRequest;
import com.unibook.app.dto.request.loan.UpdateLoanRequest;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    // List loans
    @GetMapping
    @Operation(summary = "List loans", description = "Retrieves a list of all loans and returns their details.", tags = {"Loan Endpoints"})
    public List<LoanResponse> getAll() {
        return loanService.findAll();
    }

    // Find Loan by id
    @GetMapping("/{id}")
    @Operation(summary = "List loans", description = "Retrieves a list of all loans and returns their details.", tags = {"Loan Endpoints"})
    public LoanResponse findById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    // Create Loan
    @PostMapping
    @Operation(summary = "Create a loan",description = "Creates a new loan.", tags = {"Loan Endpoints"})
    public LoanResponse create(@RequestBody CreateLoanRequest request) {
        return loanService.createLoan(request);
    }

    // Return Loan
    @PostMapping("/{id}/return")
    @Operation(summary = "Return a loan",description = "Marks a loan as returned.", tags = {"Loan Endpoints"})
    public LoanResponse returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    // Partial update
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update loan", description = "Partially updates an existing loan with the provided details and returns the updated loan.", tags = {"Loan Endpoints"})
    public LoanResponse partialUpdate( @PathVariable Long id, @RequestBody UpdateLoanRequest request ) {
        return loanService.update(id, request, true);
    }

}
