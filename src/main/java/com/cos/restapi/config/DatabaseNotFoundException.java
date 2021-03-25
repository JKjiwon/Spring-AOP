package com.cos.restapi.config;

public class DatabaseNotFoundException extends RuntimeException {
    public DatabaseNotFoundException() {
        super();
    }

    public DatabaseNotFoundException(String message) {
        super(message);
    }

    public DatabaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseNotFoundException(Throwable cause) {
        super(cause);
    }
}
