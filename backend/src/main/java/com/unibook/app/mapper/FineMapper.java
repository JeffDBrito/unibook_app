package com.unibook.app.mapper;

import com.unibook.app.dto.response.FineResponse;
import com.unibook.app.model.Fine;

public class FineMapper {
    
    /**
     * Convert Fine instance to FineResponse
     * @param fine
     * @return FineResponse
     */
    public static FineResponse toResponse(Fine fine) {
        FineResponse response = new FineResponse();
        response.setId(fine.getId());
        response.setAmount(fine.getAmount());
        response.setReason(fine.getReason());
        response.setIssuedDate(fine.getIssuedDate());
        response.setPaidDate(fine.getPaidDate());
        response.setStatus(fine.getStatus().name());
        
        response.setLoan(LoanMapper.toResponse(fine.getLoan()));

        return response;
    }

}
