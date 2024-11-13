package com.paysys.indMojaloopCustomer.model;

public class QrCode {
    private String alias;
    private String aliasType;
    private String amount;
    private String note;
    private String qrType;

    public QrCode(String qrType, String alias, String aliasType, String amount, String note) {
        this.qrType = qrType;
        this.alias = alias;
        this.aliasType = aliasType;
        this.amount = amount;
        this.note = note;

    }

    public QrCode(String qrType, String alias, String aliasType, String amount) {
        this.qrType = qrType;
        this.alias = alias;
        this.aliasType = aliasType;
        this.amount = amount;
        this.note = "";

    }

    public QrCode(String alias, String aliasType, String qrType) {
        this.alias = alias;
        this.aliasType = aliasType;
        this.qrType = qrType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasType() {
        return aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    @Override
    public String toString() {
        return "QrCode{" +
                "alias='" + alias + '\'' +
                ", aliasType='" + aliasType + '\'' +
                ", amount='" + amount + '\'' +
                ", note='" + note + '\'' +
                ", qrType='" + qrType + '\'' +
                '}';
    }
}
