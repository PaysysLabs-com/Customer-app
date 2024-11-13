package com.paysys.indMojaloopCustomer.model.Respose;

public class BeneficiaryDetailsListResponse
{
    private BeneficiaryDetailsList[] beneficiaryDetailsList;

    public BeneficiaryDetailsList[] getBeneficiaryDetailsList ()
    {
        return beneficiaryDetailsList;
    }

    public void setBeneficiaryDetailsList (BeneficiaryDetailsList[] beneficiaryDetailsList)
    {
        this.beneficiaryDetailsList = beneficiaryDetailsList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [beneficiaryDetailsList = "+beneficiaryDetailsList+"]";
    }
}
