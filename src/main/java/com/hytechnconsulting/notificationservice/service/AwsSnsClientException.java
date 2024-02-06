package com.hytechnconsulting.notificationservice.service;

public class AwsSnsClientException extends RuntimeException {

    public AwsSnsClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
