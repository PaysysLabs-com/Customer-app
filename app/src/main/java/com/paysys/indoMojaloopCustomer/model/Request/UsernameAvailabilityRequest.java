package com.paysys.indMojaloopCustomer.model.Request;

public class UsernameAvailabilityRequest {

       String channel ;
       String customerType ;
       String deviceType ;
       String newPassword ;
       String oldPassword ;
       String token ;
       String username ;

    public UsernameAvailabilityRequest(String channel, String customerType, String deviceType, String newPassword,
                                       String oldPassword, String token, String username) {
        this.channel = channel;
        this.customerType = customerType;
        this.deviceType = deviceType;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.token = token;
        this.username = username;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UsernameAvailabilityRequest{" +
                "channel='" + channel + '\'' +
                ", customerType='" + customerType + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
