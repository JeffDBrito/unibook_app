package com.unibook.app.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.loan.CreateLoanRequest;
import com.unibook.app.dto.request.loan.RequestLoanRequest;
import com.unibook.app.dto.response.LoanRequestResponse;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.enums.CopyStatus;
import com.unibook.app.enums.LoanRequestStatus;
import com.unibook.app.exceptions.BadRequestException;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.LoanMapper;
import com.unibook.app.model.Book;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Loan;
import com.unibook.app.model.LoanRequest;
import com.unibook.app.model.User;
import com.unibook.app.repository.BookRepository;
import com.unibook.app.repository.CopyRepository;
import com.unibook.app.repository.LoanRepository;
import com.unibook.app.repository.LoanRequestRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.unibook.app.mapper.LoanRequestMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final LoanRequestRepository loanRequestRepository;
    private final LoanService loanService;
    private final SecurityService securityService;


    // --------------------- //
    // Management Operations //
    // --------------------- //

    @Transactional
    public LoanRequestResponse requestLoan(
        User authenticatedUser,
        RequestLoanRequest request
    ) {
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "bookId",
                    "Book not found"
                )
            );

        boolean hasAvailableCopy =
            copyRepository.existsByBookIdAndDeletedAtIsNull(
                book.getId()
            );

        if (!hasAvailableCopy) {
            throw new BadRequestException(
                "message",
                "There are no available copies of this book"
            );
        }

        boolean alreadyRequested =
            loanRequestRepository.existsByUserIdAndBookIdAndStatus(
                authenticatedUser.getId(),
                book.getId(),
                LoanRequestStatus.PENDING
            );

        if (alreadyRequested) {
            throw new BadRequestException(
                "bookId",
                "You already have a pending request for this book"
            );
        }

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setUser(authenticatedUser);
        loanRequest.setBook(book);
        loanRequest.setStatus(LoanRequestStatus.PENDING);

        return LoanRequestMapper.toResponse(
            loanRequestRepository.save(loanRequest)
        );
    }

    @Transactional
    public LoanResponse approveRequest(Long requestId) {

        LoanRequest loanRequest = loanRequestRepository.findById(requestId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "requestId",
                    "Loan request not found"
                )
            );

        if (loanRequest.getStatus() != LoanRequestStatus.PENDING) {
            throw new BadRequestException(
                "requestId",
                "This loan request is no longer pending"
            );
        }

        Copy copy = copyRepository
            .findFirstByBookIdAndStatusAndDeletedAtIsNull(
                loanRequest.getBook().getId(),
                CopyStatus.AVAILABLE
            )
            .orElseThrow(() ->
                new BadRequestException(
                    "bookId",
                    "There are no available copies of this book"
                )
            );

        CreateLoanRequest createLoanRequest = new CreateLoanRequest();
        createLoanRequest.setUserId(loanRequest.getUser().getId());
        createLoanRequest.setCopyId(copy.getId());

        // Set due date to 30 days from now
        LocalDate dueDate = LocalDate.now().plusDays(30);
        createLoanRequest.setDueDate(dueDate);

        LoanResponse loan = loanService.createLoan(createLoanRequest);

        loanRequest.setStatus(LoanRequestStatus.APPROVED);
        loanRequestRepository.save(loanRequest);

        return loan;
    }

    @Transactional
    public LoanRequestResponse rejectRequest(Long requestId) {

        LoanRequest loanRequest = loanRequestRepository.findById(requestId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "requestId",
                    "Loan request not found"
                )
            );

        if (loanRequest.getStatus() != LoanRequestStatus.PENDING) {
            throw new BadRequestException(
                "requestId",
                "This loan request is no longer pending"
            );
        }

        loanRequest.setStatus(LoanRequestStatus.REJECTED);
        loanRequestRepository.save(loanRequest);

        loanRequest = loanRequestRepository.findById(requestId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "requestId",
                    "Loan request not found"
                )
            );

        return LoanRequestMapper.toResponse(loanRequest);
    }

    @Transactional
    public LoanRequestResponse cancelRequest(Long requestId) {

        LoanRequest loanRequest = loanRequestRepository.findById(requestId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "requestId",
                    "Loan request not found"
                )
            );

        if (loanRequest.getStatus() != LoanRequestStatus.PENDING) {
            throw new BadRequestException(
                "requestId",
                "This loan request is no longer pending"
            );
        }

        // Check if the authenticated user is the owner of the request
        User currentUser = securityService.getCurrentUser();
        if (!loanRequest.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException(
                "requestId",
                "You are not authorized to cancel this loan request"
            );
        }

        loanRequest.setStatus(LoanRequestStatus.CANCELLED);
        loanRequestRepository.save(loanRequest);

        loanRequest = loanRequestRepository.findById(requestId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "requestId",
                    "Loan request not found"
                )
            );
        
        return LoanRequestMapper.toResponse(loanRequest);
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch Loan by id
     * @param id
     * @return LoanResponse
     */
    public LoanResponse findById(Long id){
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("id", "Loan not found"));
        
        return LoanMapper.toResponse(loan);
    }

    /**
     * Fetch all Loans
     * @return List<LoanResponse>
     */
    public Page<LoanRequestResponse> findRequests(
        String search,
        LoanRequestStatus status,
        Pageable pageable
    ) {
        Page<LoanRequest> requests;

        if (search == null || search.isBlank()) {
            requests = loanRequestRepository.findByStatus(
                status,
                pageable
            );
        } else {
            requests = loanRequestRepository.searchByStatus(
                status,
                search.trim(),
                pageable
            );
        }

        return requests.map(LoanRequestMapper::toResponse);
    }

}
