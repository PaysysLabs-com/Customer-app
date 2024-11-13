package com.paysys.indMojaloopCustomer.model.Request;

public class AddBeneficiaryRequest {

    private String alias;

    private String aliasType;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliastype() {
        return aliasType;
    }

    public void setAliastype(String aliastype) {
        this.aliasType = aliastype;
    }

    public AddBeneficiaryRequest(String alias, String aliasType) {
        this.alias = alias;
        this.aliasType = aliasType;
    }

    @Override
    public String toString() {
        return "AddBeneficiaryRequest{" +
                "alias='" + alias + '\'' +
                ", aliastype='" + aliasType + '\'' +
                '}';
    }

}
