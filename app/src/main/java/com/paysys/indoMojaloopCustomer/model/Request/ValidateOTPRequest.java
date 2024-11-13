package com.paysys.indMojaloopCustomer.model.Request;

public class ValidateOTPRequest {

    private String token;

    public ValidateOTPRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "EnterDataRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
