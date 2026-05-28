package com.unibook.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.fine.CreateFineRequest;
import com.unibook.app.dto.response.FineResponse;
import com.unibook.app.enums.FineStatus;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.FineMapper;
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
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Fine fine = new Fine();
        fine.setAmount(request.getAmount());
        fine.setReason(request.getReason());
        fine.setIssuedDate(LocalDate.now());
        fine.setStatus(FineStatus.PENDING);
        fine.setLoan(loan);

        return FineMapper.toResponse(fineRepository.save(fine));
    }

    /**
     * Pay Fine
     * @param id
     * @return FineResponse
     */
    public FineResponse payFine(Long id) {
        Fine fine = fineRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        fine.setStatus(FineStatus.PAID);
        fine.setPaidDate(LocalDate.now());

        return FineMapper.toResponse(fineRepository.save(fine));
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
        return fines.stream().map(FineMapper::toResponse).toList();
    }

    /**
     * Find Fine by id
     * @param id
     * @return
     */
    public FineResponse findById(Long id){
        Fine fine = fineRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        return FineMapper.toResponse(fine);
    }

}
