package com.container.maersk.controller;

public class MissingContainerDetailsException extends Exception {
    public MissingContainerDetailsException(String errorMessage) {
        super(errorMessage);
    }
}
