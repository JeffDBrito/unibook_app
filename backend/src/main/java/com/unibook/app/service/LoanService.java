package com.unibook.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.loan.CreateLoanRequest;
import com.unibook.app.dto.request.loan.UpdateLoanRequest;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.enums.CopyStatus;
import com.unibook.app.enums.LoanStatus;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.UserMapper;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Loan;
import com.unibook.app.model.User;
import com.unibook.app.repository.CopyRepository;
import com.unibook.app.repository.LoanRepository;
import com.unibook.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;
    private final CopyService copyService;


    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create a Loan
     * @param request
     * @return LoanResponse
     */
    public LoanResponse createLoan(CreateLoanRequest request) {

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Copy copy = copyRepository.findById(request.getCopyId())
            .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        if (copy.getStatus() != CopyStatus.AVAILABLE) {
            throw new ResourceNotFoundException("Copy is not available");
        }

        Loan loan = new Loan();

        loan.setUser(user);
        loan.setCopy(copy);

        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(request.getDueDate());

        loan.setStatus(LoanStatus.ACTIVE);

        copy.setStatus(CopyStatus.RENTED);
        copy.setInventory(null);

        copyRepository.save(copy);

        return toResponse(loanRepository.save(loan));
    }

    /**
     * Return Loan
     * @param id
     * @return
     */
    public LoanResponse returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);

        Copy copy = loan.getCopy();

        copy.setStatus(CopyStatus.AVAILABLE);

        copyRepository.save(copy);

        return toResponse(loanRepository.save(loan));
    }

    /**
     * Soft Delete a loan
     * @param id
     */
    public void deleteById(Long id){
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        loan.softDelete();
        loanRepository.save(loan);
    }

    /**
     * Restore a loan
     * @param id
     */
    public LoanResponse restoreById(Long id){
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        loan.restore();
        return toResponse(loanRepository.save(loan));
    }

    /**
     * Update Loan
     * @param id
     * @param request
     * @param partial
     * @return LoanResponse
     */
    public LoanResponse update(Long id, UpdateLoanRequest request, boolean partial){

        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if(!partial || request.getReturnDate() != null){
            loan.setReturnDate(request.getReturnDate());
        }

        if(!partial || request.getStatus() != null){
            loan.setStatus(request.getStatus());
        }
        
        return toResponse(loanRepository.save(loan));

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
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        
        return toResponse(loan);
    }

    /**
     * Fetch all Loans
     * @return List<LoanResponse>
     */
    public List<LoanResponse> findAll(){
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::toResponse).toList();
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert a Loan instance to LoanResponse
     * @param loan
     * @return
     */ // TODO: Create a Mapper
    public LoanResponse toResponse(Loan loan) {

        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setLoanDate(loan.getLoanDate());
        response.setDueDate(loan.getDueDate());
        response.setReturnDate(loan.getReturnDate());
        
        response.setCopy(copyService.toResponse(loan.getCopy()));
        response.setUser(UserMapper.toResponse(loan.getUser()));

        response.setStatus(loan.getStatus().name());

        return response;
    }

}
