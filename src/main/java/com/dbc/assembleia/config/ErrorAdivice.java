package com.dbc.assembleia.config;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class ErrorAdivice {

    private final String code;
    private final String error;

    public ErrorAdivice(@NonNull String code, String error) {
        this.code = Objects.requireNonNull(code);
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
