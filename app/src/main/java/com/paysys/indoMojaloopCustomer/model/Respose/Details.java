package com.paysys.indMojaloopCustomer.model.Respose;

public class Details
{
    private String customerType;

    private String isFirstLogin;

    private String showTnc;

    private String institutionCode;

    private String mobileNumber;

    private String aliasType;

    private String alias;

    private String lastLoginDate;

    private String accountNumber;

    private String email;

    private String username;

    public String getCustomerType ()
    {
        return customerType;
    }

    public void setCustomerType (String customerType)
    {
        this.customerType = customerType;
    }

    public String getIsFirstLogin ()
    {
        return isFirstLogin;
    }

    public void setIsFirstLogin (String isFirstLogin)
    {
        this.isFirstLogin = isFirstLogin;
    }

    public String getShowTnc ()
    {
        return showTnc;
    }

    public void setShowTnc (String showTnc)
    {
        this.showTnc = showTnc;
    }

    public String getInstitutionCode ()
    {
        return institutionCode;
    }

    public void setInstitutionCode (String institutionCode)
    {
        this.institutionCode = institutionCode;
    }

    public String getMobileNumber ()
    {
        return mobileNumber;
    }

    public void setMobileNumber (String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getAliasType ()
    {
        return aliasType;
    }

    public void setAliasType (String aliasType)
    {
        this.aliasType = aliasType;
    }

    public String getAlias ()
    {
        return alias;
    }

    public void setAlias (String alias)
    {
        this.alias = alias;
    }

    public String getLastLoginDate ()
    {
        return lastLoginDate;
    }

    public void setLastLoginDate (String lastLoginDate)
    {
        this.lastLoginDate = lastLoginDate;
    }

    public String getAccountNumber ()
    {
        return accountNumber;
    }

    public void setAccountNumber (String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [customerType = "+customerType+", isFirstLogin = "+isFirstLogin+", showTnc = "+showTnc+", institutionCode = "+institutionCode+", mobileNumber = "+mobileNumber+", aliasType = "+aliasType+", alias = "+alias+", lastLoginDate = "+lastLoginDate+", accountNumber = "+accountNumber+", email = "+email+"]";
    }
}

