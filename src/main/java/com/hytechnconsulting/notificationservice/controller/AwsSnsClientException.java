package com.hytechnconsulting.notificationservice.controller;

public class AwsSnsClientException extends RuntimeException {

    public AwsSnsClientException(String message) {
        super(message);
    }

    public AwsSnsClientException(String message, Throwable cause) {
        super(message, cause);
    }
}