package com.paysys.indMojaloopCustomer.model.Respose;

public class TransactionDetails {

    private String date;

    private String displayMessage;

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getDisplayMessage ()
    {
        return displayMessage;
    }

    public void setDisplayMessage (String displayMessage)
    {
        this.displayMessage = displayMessage;
    }

    @Override
    public String toString()
    {
        return "TransactionDetails [date = "+date+", displayMessage = "+displayMessage+"]";
    }
}
