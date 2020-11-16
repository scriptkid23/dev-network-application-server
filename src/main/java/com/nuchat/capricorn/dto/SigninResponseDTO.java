package com.nuchat.capricorn.dto;

public class SigninResponseDTO{
    private String email;
    private String token;

    public SigninResponseDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }


}