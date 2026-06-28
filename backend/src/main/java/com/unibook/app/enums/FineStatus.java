package com.unibook.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FineStatus {
    PENDING,
    PAID,
    CANCELLED;

    @JsonCreator
    public static FineStatus fromValue(String value){

        for(FineStatus status : values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }

        throw new IllegalArgumentException(
            "Invalid Fine status"
        );
    }
}
