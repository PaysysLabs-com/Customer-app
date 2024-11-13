package com.paysys.indMojaloopCustomer.model.Request;

public class RegistrationAliasRequest {

    private String alias;
    private String aliasType;
    private String currency;
    private String secretAnswer;
    private String secretQuestion;
    private String token;

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public RegistrationAliasRequest(String alias, String aliasType, String currency, String secretAnswer,
                                    String secretQuestion, String token) {
        this.alias = alias;
        this.aliasType = aliasType;
        this.currency = currency;
        this.secretAnswer = secretAnswer;
        this.secretQuestion = secretQuestion;
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegistrationAliasRequest{" +
                "alias='" + alias + '\'' +
                ", aliasType='" + aliasType + '\'' +
                ", currency='" + currency + '\'' +
                ", secretAnswer='" + secretAnswer + '\'' +
                ", secretQuestion='" + secretQuestion + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
