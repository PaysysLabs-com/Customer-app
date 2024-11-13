package com.paysys.indMojaloopCustomer.utils;

public class Log {
    static boolean LOG = true;
    static final String TAG = "Mojaloop";

    public static void i(String string) {
        if (LOG) {
        	if(string== null) string = "QQ>>--null string for log";
        	android.util.Log.i(TAG, string);
        }
    }
    public static void e(String string) {
        if (LOG) {
            if(string== null) string = "QQ>>--null string for log";
        	android.util.Log.e(TAG,"QQ>>--"+ string);
        }
    }
    public static void d(String string) {
        if (LOG) {
            if(string== null) string = "QQ>>--null string for log";
        	android.util.Log.d(TAG,"QQ>>--"+ string);
        }
    }
    public static void v(String string) {
        if (LOG) {
            if(string== null) string = "QQ>>--null string for log";
        	android.util.Log.v(TAG,"QQ>>--"+  string);
        }
    }
    public static void w(String string) {
        if (LOG) {
            if(string== null) string = "QQ>>--null string for log";
        	android.util.Log.w(TAG,"QQ>>--"+  string);
        }
    }
}