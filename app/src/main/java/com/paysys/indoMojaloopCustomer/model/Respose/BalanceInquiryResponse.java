package com.paysys.indMojaloopCustomer.model.Respose;

public class BalanceInquiryResponse {

    private String institutionCode;
    private String actualBalance;
    private String availableBalance;

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(String actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [institutionCode = "+institutionCode+", actualBalance = "+actualBalance+", availableBalance = "+availableBalance+"]";
    }
}
