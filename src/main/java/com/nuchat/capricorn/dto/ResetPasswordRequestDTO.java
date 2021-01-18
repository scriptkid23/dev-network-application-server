package com.nuchat.capricorn.dto;

public class ResetPasswordRequestDTO {
    private String password;
    private String cfpassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCfpassword() {
        return cfpassword;
    }

    public void setCfpassword(String cfpassword) {
        this.cfpassword = cfpassword;
    }
}
