package com.hytechnconsulting.notificationservice.dto;
import lombok.Getter;

@Getter
public class SmsRequestDto {
    private String mobileNo;
    private String message;
}

