package com.paysys.indMojaloopCustomer.model.Respose;

public class BeneficiaryDetailsList
{
    private String aliasType;

    private String alias;

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

    @Override
    public String toString()
    {
        return "ClassPojo [aliasType = "+aliasType+", alias = "+alias+"]";
    }
}
