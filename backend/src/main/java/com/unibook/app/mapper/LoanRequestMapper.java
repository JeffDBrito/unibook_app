package com.unibook.app.mapper;

import com.unibook.app.dto.response.LoanRequestResponse;
import com.unibook.app.model.LoanRequest;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoanRequestMapper {
    
    /**
     * Convert LoanRequest instance to LoanRequestResponse
     * @param fine
     * @return LoanRequestResponse
     */
    public static LoanRequestResponse toResponse(LoanRequest request) {
        LoanRequestResponse response = new LoanRequestResponse();

        response.setId(request.getId());

        response.setUserId(request.getUser().getId());
        response.setUserName(request.getUser().getPerson().getName());
        response.setUserLogin(request.getUser().getLogin());

        response.setBookId(request.getBook().getId());
        response.setBookTitle(request.getBook().getTitle());
        response.setIsbn(request.getBook().getIsbn());

        response.setStatus(request.getStatus().name());
        response.setRequestedAt(request.getCreatedAt());

        return response;
    }

}
