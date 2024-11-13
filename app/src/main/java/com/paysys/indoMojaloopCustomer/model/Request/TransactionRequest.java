package com.paysys.indMojaloopCustomer.model.Request;

public class TransactionRequest {

    private String token;
    private String transactionType;
    private String transferId;

    public TransactionRequest(String token, String transactionType, String transferId) {
        this.token = token;
        this.transferId = transferId;
        this.transactionType = transactionType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "token='" + token + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transferId='" + transferId + '\'' +
                '}';
    }
}
