package com.paysys.indMojaloopCustomer.model;

public class StepsHeader {

    private String stepCount;
    private String stepName;
    private String RegistrationHeader;

    public StepsHeader(String stepCount, String stepName, String registrationHeader) {
        this.stepCount = stepCount;
        this.stepName = stepName;
        RegistrationHeader = registrationHeader;
    }

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getRegistrationHeader() {
        return RegistrationHeader;
    }

    public void setRegistrationHeader(String registrationHeader) {
        RegistrationHeader = registrationHeader;
    }
}
