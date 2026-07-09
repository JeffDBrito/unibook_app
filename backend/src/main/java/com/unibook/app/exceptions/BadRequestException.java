package com.unibook.app.exceptions;

import lombok.Getter;

public class BadRequestException extends RuntimeException {

    @Getter
    private final String field;

    public BadRequestException(String field, String message) {
        super(message);
        this.field = field;
    }

}