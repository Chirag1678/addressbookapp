package com.bridgelabz.addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ResponseDTO {
    // Attributes
    private String message;
    private int statusCode;
    private Object data;
}
