package com.unibook.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CopyStatus {

    RENTED,
    AVAILABLE,
    LOST,
    DAMAGED,
    REASSIGNED;

    @JsonCreator
    public static CopyStatus fromValue(String value){

        for(CopyStatus status : values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }

        throw new IllegalArgumentException(
            "Invalid Copy status"
        );
    }
    
}
