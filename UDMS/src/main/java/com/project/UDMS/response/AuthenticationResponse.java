package com.project.UDMS.response;

public class AuthenticationResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return this.token;
    }

}
