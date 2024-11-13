package com.paysys.indMojaloopCustomer.utils;

/**
 * Created by Qasim on 10/10/2017.
 */

public class DeviceDetails {

    String deviceOrigin;
    String deviceOS;
    String deviceModel;
    String deviceVersion;
    String deviceData;

    public DeviceDetails() {
    }

    public DeviceDetails(String deviceOrigin, String deviceOS, String deviceModel) {
        this.deviceOrigin = deviceOrigin;
        this.deviceOS = deviceOS;
        this.deviceModel = deviceModel;
    }

    public String getDeviceOrigin() {
        return deviceOrigin;
    }

    public void setDeviceOrigin(String deviceOrigin) {
        this.deviceOrigin = deviceOrigin;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }
}
