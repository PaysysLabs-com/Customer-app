package com.paysys.indMojaloopCustomer.model.Respose;

public class CreditUnionList
{
    private String cu_name;
    private String federation_name;
    private String cu_display;
    private String currency;
    private String cu_country;
    private String institution_code;
    private String fsp_id;

    public String getCu_name ()
    {
        return cu_name;
    }

    public void setCu_name (String cu_name)
    {
        this.cu_name = cu_name;
    }

    public String getFederation_name ()
    {
        return federation_name;
    }

    public void setFederation_name (String federation_name)
    {
        this.federation_name = federation_name;
    }

    public String getCu_display ()
    {
        return cu_display;
    }

    public void setCu_display (String cu_display)
    {
        this.cu_display = cu_display;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getCu_country ()
    {
        return cu_country;
    }

    public void setCu_country (String cu_country)
    {
        this.cu_country = cu_country;
    }

    public String getInstitution_code ()
    {
        return institution_code;
    }

    public void setInstitution_code (String institution_code)
    {
        this.institution_code = institution_code;
    }

    public String getFsp_id() {
        return fsp_id;
    }

    public void setFsp_id(String fsp_id) {
        this.fsp_id = fsp_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cu_name = "+cu_name+", federation_name = "+federation_name+", cu_display = "+cu_display+", currency = "+currency+", cu_country = "+cu_country+", institution_code = "+institution_code+"]";
    }
}


