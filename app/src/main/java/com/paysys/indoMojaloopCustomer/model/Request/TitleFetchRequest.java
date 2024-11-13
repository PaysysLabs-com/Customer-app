package com.paysys.indMojaloopCustomer.model.Request;

public class TitleFetchRequest {

    private String amountType;
    private String transactionType;
    private String note;
    private String titleFetchAliasType;
    private String amount;
    private String fromAlias;
    private String from_account;
    private String institutionCode;
    private String currency;
    private String titleFetchAlias;
    private String fromAliasType;

    public TitleFetchRequest(String amountType, String transactionType, String note, String titleFetchAliasType,
                              String amount, String fromAlias,
                             String from_account, String institutionCode, String currency,
                             String titleFetchAlias, String fromAliasType) {
        this.titleFetchAliasType = titleFetchAliasType;
        this.amountType = amountType;
        this.transactionType = transactionType;
        this.note = note;
        this.amount = amount;
        this.fromAlias = fromAlias;
        this.from_account = from_account;
        this.institutionCode = institutionCode;
        this.currency = currency;
        this.titleFetchAlias = titleFetchAlias;
        this.fromAliasType = fromAliasType;
    }

    public String getAmountType ()
    {
        return amountType;
    }

    public void setAmountType (String amountType)
    {
        this.amountType = amountType;
    }

    public String getTransactionType ()
    {
        return transactionType;
    }

    public void setTransactionType (String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getNote ()
    {
        return note;
    }

    public void setNote (String note)
    {
        this.note = note;
    }

    public String getTitleFetchAliasType ()
    {
        return titleFetchAliasType;
    }

    public void setTitleFetchAliasType (String titleFetchAliasType)
    {
        this.titleFetchAliasType = titleFetchAliasType;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getFromAlias ()
    {
        return fromAlias;
    }

    public void setFromAlias (String fromAlias)
    {
        this.fromAlias = fromAlias;
    }

    public String getFrom_account ()
    {
        return from_account;
    }

    public void setFrom_account (String from_account)
    {
        this.from_account = from_account;
    }

    public String getInstitutionCode ()
    {
        return institutionCode;
    }

    public void setInstitutionCode (String institutionCode)
    {
        this.institutionCode = institutionCode;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getTitleFetchAlias ()
    {
        return titleFetchAlias;
    }

    public void setTitleFetchAlias (String titleFetchAlias)
    {
        this.titleFetchAlias = titleFetchAlias;
    }

    public String getFromAliasType ()
    {
        return fromAliasType;
    }

    public void setFromAliasType (String fromAliasType)
    {
        this.fromAliasType = fromAliasType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amountType = "+amountType+", transactionType = "+transactionType+", note = "+note+", titleFetchAliasType = "+titleFetchAliasType+", amount = "+amount+", fromAlias = "+fromAlias+", from_account = "+from_account+", institutionCode = "+institutionCode+", currency = "+currency+", titleFetchAlias = "+titleFetchAlias+", fromAliasType = "+fromAliasType+"]";
    }

}
