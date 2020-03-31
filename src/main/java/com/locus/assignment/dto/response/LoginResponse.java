package com.locus.assignment.dto.response;

public class LoginResponse {

    private String token;
    private long expiryInMillis;

    public LoginResponse(String token, long expiryInMillis){
        this.token = token;
        this.expiryInMillis = expiryInMillis;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiryInMillis() {
        return expiryInMillis;
    }

    public void setExpiryInMillis(long expiryInMillis) {
        this.expiryInMillis = expiryInMillis;
    }
}
