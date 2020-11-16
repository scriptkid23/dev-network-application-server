package com.nuchat.capricorn.dto;

import org.springframework.http.HttpStatus;

public class MessageDTO {
    private HttpStatus code;
    private String message;

    public MessageDTO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }


    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
