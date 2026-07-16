package com.unibook.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LoanRequestStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED;

    @JsonCreator
    public static LoanRequestStatus fromValue(String value){

        for(LoanRequestStatus status : values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }

        throw new IllegalArgumentException(
            "Invalid Loan status"
        );
    }
}
