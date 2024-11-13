package com.paysys.indMojaloopCustomer.model.Respose;

public class TitleFetchResponse {
    private String transactionAllowed;
    private String institutionCode;
    private String accountTitle;
    private String transferId;
    private String isTransactionAllowed;
    private String token;
    private String feeAmount;
    private String sourceFeeAmount;

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTransactionAllowed ()
    {
        return transactionAllowed;
    }

    public void setTransactionAllowed (String transactionAllowed)
    {
        this.transactionAllowed = transactionAllowed;
    }

    public String getInstitutionCode ()
    {
        return institutionCode;
    }

    public void setInstitutionCode (String institutionCode)
    {
        this.institutionCode = institutionCode;
    }

    public String getAccountTitle ()
    {
        return accountTitle;
    }

    public void setAccountTitle (String accountTitle)
    {
        this.accountTitle = accountTitle;
    }

    public String getTransferId ()
    {
        return transferId;
    }

    public void setTransferId (String transferId)
    {
        this.transferId = transferId;
    }

    public String getIsTransactionAllowed ()
    {
        return isTransactionAllowed;
    }

    public void setIsTransactionAllowed (String isTransactionAllowed)
    {
        this.isTransactionAllowed = isTransactionAllowed;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public String getSourceFeeAmount() {
        return sourceFeeAmount;
    }

    public void setSourceFeeAmount(String sourceFeeAmount) {
        this.sourceFeeAmount = sourceFeeAmount;
    }

    @Override
    public String toString() {
        return "TitleFetchResponse{" +
                "transactionAllowed='" + transactionAllowed + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                ", accountTitle='" + accountTitle + '\'' +
                ", transferId='" + transferId + '\'' +
                ", isTransactionAllowed='" + isTransactionAllowed + '\'' +
                ", token='" + token + '\'' +
                ", feeAmount='" + feeAmount + '\'' +
                ", sourceFeeAmount='" + sourceFeeAmount + '\'' +
                '}';
    }
}
