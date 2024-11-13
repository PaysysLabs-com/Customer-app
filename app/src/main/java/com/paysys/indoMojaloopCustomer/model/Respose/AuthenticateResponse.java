package com.paysys.indMojaloopCustomer.model.Respose;

public class AuthenticateResponse {
    private Details details;

    private String token;

    public Details getDetails ()
    {
        return details;
    }

    public void setDetails (Details details)
    {
        this.details = details;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [details = "+details+", token = "+token+"]";
    }
}