package com.paysys.indMojaloopCustomer.model;

public class InstitutionCodeList {

    String label ;
    String value;

    public InstitutionCodeList(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "InstitutionCodeList{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
