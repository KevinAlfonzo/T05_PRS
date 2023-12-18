package com.t06.dev.springwebfluxapi.transactional.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Resource Not Found Exception";

    public ResourceNotFoundException(String detail) {
        super(String.format("%s - %s", DESCRIPTION, detail));
    }
}
