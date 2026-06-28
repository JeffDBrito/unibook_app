package com.unibook.app.mapper;

import com.unibook.app.dto.request.loan.PartialUpdateLoanRequest;
import com.unibook.app.dto.request.loan.UpdateLoanRequest;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.model.Loan;

public class LoanMapper {
    /**
     * Convert a Loan instance to LoanResponse
     * @param loan
     * @return LoanResponse
     */ 
    public static LoanResponse toResponse(Loan loan) {

        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setLoanDate(loan.getLoanDate());
        response.setDueDate(loan.getDueDate());
        response.setReturnDate(loan.getReturnDate());
        
        response.setCopy(CopyMapper.toResponse(loan.getCopy()));
        response.setUser(UserMapper.toResponse(loan.getUser()));

        response.setStatus(loan.getStatus().name());

        return response;
    }

    /**
     * Convert full update dto to partial update dto
     * @param request
     * @return PartialUpdateLoanRequest
     */
    public static PartialUpdateLoanRequest toPartialUpdate(UpdateLoanRequest request){
        PartialUpdateLoanRequest partial = new PartialUpdateLoanRequest();
        partial.setStatus(request.getStatus());
        return partial;
    }
}
