package com.unibook.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.fine.CreateFineRequest;
import com.unibook.app.dto.response.FineResponse;
import com.unibook.app.enums.FineStatus;
import com.unibook.app.model.Fine;
import com.unibook.app.model.Loan;
import com.unibook.app.repository.FineRepository;
import com.unibook.app.repository.LoanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final LoanRepository loanRepository;
    private final LoanService loanService;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Fine
     * @param request
     * @return
     */
    public FineResponse createFine(CreateFineRequest request) {
        Loan loan = loanRepository.findById(request.getLoanId())
            .orElseThrow(() -> new RuntimeException("Loan not found"));

        Fine fine = new Fine();
        fine.setAmount(request.getAmount());
        fine.setReason(request.getReason());
        fine.setIssuedDate(LocalDate.now());
        fine.setStatus(FineStatus.PENDING);
        fine.setLoan(loan);

        return toResponse(fineRepository.save(fine));
    }

    /**
     * Pay Fine
     * @param id
     * @return FineResponse
     */
    public FineResponse payFine(Long id) {
        Fine fine = fineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fine not found"));

        fine.setStatus(FineStatus.PAID);
        fine.setPaidDate(LocalDate.now());

        return toResponse(fineRepository.save(fine));
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Fines
     * @return List<FineResponse>
     */
    public List<FineResponse> findAll() {
        List<Fine> fines = fineRepository.findAll();
        return fines.stream().map(this::toResponse).toList();
    }

    /**
     * Find Fine by id
     * @param id
     * @return
     */
    public FineResponse findById(Long id){
        Fine fine = fineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fine not found"));

        return toResponse(fine);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Fine instance to FineResponse
     * @param fine
     * @return FineResponse
     */ // TODO: Create a Mapper
    private FineResponse toResponse(Fine fine) {
        FineResponse response = new FineResponse();
        response.setId(fine.getId());
        response.setAmount(fine.getAmount());
        response.setReason(fine.getReason());
        response.setIssuedDate(fine.getIssuedDate());
        response.setPaidDate(fine.getPaidDate());
        response.setStatus(fine.getStatus().name());
        
        response.setLoan(loanService.toResponse(fine.getLoan()));

        return response;
    }

}
