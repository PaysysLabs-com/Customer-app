package com.paysys.indMojaloopCustomer.utils;


public class Constants {

    public static int READ_TIMEOUT = 90;
    public static int CONNECT_TIMEOUT = 15;
    public static String str_IMEI ="";

//    public static String BASE_URL ="10.0.115.131:3014";
    public static String BASE_URL ="202.63.222.243:3014";

    public static final String PROCESSED_OK = "00";
    public static final String ACCOUNT_INVALID = "006";
    public static final String SESSION_INVALID = "14";
    public static final String SESSION_EXPIRED = "503";
    public static final String ACCOUNT_CLOSD = "005";
    public static final String INVALID_LOGIN = "607";
    public static final String MULTI_LOGIN = "609";
    public static final String INACTIVE_USER = "610";
    public static final String LOCKED_USER = "508";

    public static DeviceDetails deviceDetails = new DeviceDetails();
    public static String deviceToken = "";
    public static String deviceOSVersion = "";
    public static String benefName;

    public static String deviceLanguage;
}