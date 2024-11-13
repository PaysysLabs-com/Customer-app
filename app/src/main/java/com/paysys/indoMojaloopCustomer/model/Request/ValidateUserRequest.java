package com.paysys.indMojaloopCustomer.model.Request;

public class ValidateUserRequest {

    private String accountNumber;
    private String appVersion;
    private String channel;
    private String customerType;
    private String deviceToken;
    private String deviceID;
    private String deviceType;
    private String dob;
    private String email;
    private String fullName;
    private String institutionCode;
    private String latitude;
    private String longitude;
    private String mobileNumber;
    private String fsp_id;


    public ValidateUserRequest(String accountNumber, String appVersion, String channel, String customerType, String deviceToken,
                               String deviceID, String deviceType, String dob, String email, String fullName, String institutionCode,
                               String latitude, String longitude, String mobileNumber, String fsp_id) {
        this.accountNumber = accountNumber;
        this.appVersion = appVersion;
        this.channel = channel;
        this.customerType = customerType;
        this.deviceToken = deviceToken;
        this.deviceID = deviceID;
        this.deviceType = deviceType;
        this.dob = dob;
        this.email = email;
        this.fullName = fullName;
        this.institutionCode = institutionCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mobileNumber = mobileNumber;
        this.fsp_id = fsp_id;

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFsp_id() {
        return fsp_id;
    }

    public void setFsp_id(String fsp_id) {
        this.fsp_id = fsp_id;
    }

    @Override
    public String toString() {
        return "ValidateUserRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", channel='" + channel + '\'' +
                ", customerType='" + customerType + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
