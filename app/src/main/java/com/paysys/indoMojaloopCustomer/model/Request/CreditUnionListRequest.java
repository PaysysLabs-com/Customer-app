package com.paysys.indMojaloopCustomer.model.Request;

public class CreditUnionListRequest {

    private String country;

    public CreditUnionListRequest(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CreditUnionListRequest{" +
                "country='" + country + '\'' +
                '}';
    }
}
