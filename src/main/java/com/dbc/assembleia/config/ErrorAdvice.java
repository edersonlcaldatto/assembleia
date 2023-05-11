package com.dbc.assembleia.config;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class ErrorAdvice {

    private final String code;
    private final String error;

    public ErrorAdvice(@NonNull String code, String error) {
        this.code = Objects.requireNonNull(code);
        this.error = error;
    }

}
