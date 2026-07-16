package com.unibook.app.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.loan.RequestLoanRequest;
import com.unibook.app.dto.response.LoanRequestResponse;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.enums.LoanRequestStatus;
import com.unibook.app.model.User;
import com.unibook.app.service.LoanRequestService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/loan-requests")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService){
        this.loanRequestService = loanRequestService;
    }

    @GetMapping
    @PreAuthorize(
        "hasAnyRole('LIBRARIAN', 'ADMIN', 'SUPER_ADMIN')"
    )
    public Page<LoanRequestResponse> findRequests(
        @RequestParam(
            defaultValue = "PENDING"
        ) LoanRequestStatus status,

        @RequestParam(
            defaultValue = ""
        ) String search,

        @PageableDefault(
            size = 10,
            sort = "createdAt"
        ) Pageable pageable
    ) {
        return loanRequestService.findRequests(
            search,
            status,
            pageable
    );
}
    
    // Request Loan
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'LIBRARIAN')")
    @PostMapping
    @Operation(summary = "Request a loan",description = "Request a new loan.", tags = {"Loan Endpoints"})
    public LoanRequestResponse requestLoan(@AuthenticationPrincipal User authenticatedUser, @Valid @RequestBody RequestLoanRequest request) {
        return loanRequestService.requestLoan(authenticatedUser, request);
    }

    // Approve Loan Request
    @PreAuthorize("hasAuthority('LOAN_CREATE')")
    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve loan request", description = "Approves a loan request.", tags = {"Loan Endpoints"})
    public LoanResponse approveRequest(@PathVariable Long id) {
        return loanRequestService.approveRequest(id);
    }

    @PreAuthorize("hasAuthority('LOAN_CREATE')")
    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject loan request", description = "Rejects a loan request.", tags = {"Loan Endpoints"})
    public LoanRequestResponse rejectRequest(@PathVariable Long id) {
        return loanRequestService.rejectRequest(id);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'LIBRARIAN')")
    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel loan request", description = "Cancels a loan request.", tags = {"Loan Endpoints"})
    public LoanRequestResponse cancelRequest(@PathVariable Long id) {
        return loanRequestService.cancelRequest(id);
    }

}