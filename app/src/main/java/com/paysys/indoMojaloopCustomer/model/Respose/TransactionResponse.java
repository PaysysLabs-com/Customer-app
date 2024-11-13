package com.paysys.indMojaloopCustomer.model.Respose;

public class TransactionResponse {
    private TransactionDetails transactionDetails;

    private String rrn;

    public TransactionDetails getTransactionDetails ()
    {
        return transactionDetails;
    }

    public void setTransactionDetails (TransactionDetails transactionDetails)
    {
        this.transactionDetails = transactionDetails;
    }

    public String getRrn ()
    {
        return rrn;
    }

    public void setRrn (String rrn)
    {
        this.rrn = rrn;
    }

    @Override
    public String toString()
    {
        return "TransactionResponse [transactionDetails = "+transactionDetails+", rrn = "+rrn+"]";
    }
}
