package com.paysys.indMojaloopCustomer.model.SendParams;

public class BalanceInquiryPrram {

    private String institutionCode;
    private String accountNumber;
    private String alias;
    private String username;
    private String aliasType;
    private String actualBalance;

    public String getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(String actualBalance) {
        this.actualBalance = actualBalance;
    }

//    public BalanceInquiryPrram(St
//    ring institutionCode, String accountNumber) {
//        this.institutionCode = institutionCode;
//        this.accountNumber = accountNumber;
//    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAliasType() {
        return aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
    }
}
