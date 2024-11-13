package com.paysys.indMojaloopCustomer.model.Request;

public class BalanceInquiryRequest {

   private String  institutionCode;
   private String  savingAccountNumber;

    public BalanceInquiryRequest(String institutionCode, String savingAccountNumber) {
        this.institutionCode = institutionCode;
        this.savingAccountNumber = savingAccountNumber;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getSavingAccountNumber() {
        return savingAccountNumber;
    }

    public void setSavingAccountNumber(String savingAccountNumber) {
        this.savingAccountNumber = savingAccountNumber;
    }

    @Override
    public String toString() {
        return "BalanceInquiryRequest{" +
                "institutionCode='" + institutionCode + '\'' +
                ", savingAccountNumber='" + savingAccountNumber + '\'' +
                '}';
    }
}
