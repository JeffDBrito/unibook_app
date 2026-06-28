package com.unibook.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LoanStatus {
    ACTIVE,
    RETURNED,
    CANCELLED,
    LOST;

    @JsonCreator
    public static LoanStatus fromValue(String value){

        for(LoanStatus status : values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }

        throw new IllegalArgumentException(
            "Invalid Loan status"
        );
    }
}
