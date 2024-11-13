package com.paysys.indMojaloopCustomer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.paysys.indMojaloopCustomer.Activity.MainDrawerActivity;
import com.paysys.indMojaloopCustomer.BuildConfig;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.model.StepsHeader;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class UtilMethod {
    public static ViewFlipper addRightTransitonAnimationToView(Context context, View view){

        ((ViewFlipper)view).setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right));
        ((ViewFlipper)view).setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_from_right));
        return (ViewFlipper) view;
    }
    public static ViewFlipper addLeftTransitonAnimationToView(Context context,View view){

        ((ViewFlipper)view).setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left));
        ((ViewFlipper)view).setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_from_left));
        return (ViewFlipper) view;
    }
    public static void showToast(final String message, final MainDrawerActivity activityObj) {
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityObj, message, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public static DeviceDetails getDeviceDetails(){

        DeviceDetails deviceObj = new DeviceDetails();
        deviceObj.setDeviceOrigin("Android");
        deviceObj.setDeviceOS(Build.VERSION.RELEASE);
        deviceObj.setDeviceModel(Build.MANUFACTURER + " " + Build.MODEL);
        deviceObj.setDeviceVersion(BuildConfig.VERSION_NAME);
        return deviceObj;
    }

    public static String getConnectivityMessage(Throwable throwable){
        if(throwable !=null){
            String msg = throwable.getMessage();
            if (msg != null){
                if(msg.contains("Unable to resolve host") || msg.contains("Connection timed out") || msg.contains("failed to connect"))
                    return "Unable to establish connectivity, Please check your internet connection.";
                else
                    return msg;
            }else
                return "Request Timed Out , Please try again.";
        }else
            return "Request Timed Out, Please try again.";
    }
    public static StepsHeader steps_header(int mCurrentScreen) {
        switch (mCurrentScreen) {
            case 1:
                return new StepsHeader("1", "Personal Details", "Create Account");

            case 2:
                return new StepsHeader("2", "Bank Account", "Create Account");

            case 3:
                return new StepsHeader("3", "User Data", "Create Account");

            case 4:
                return new StepsHeader("4", "OTP confirmation", "Create Account");

            case 5:
                return new StepsHeader("", "", "Terms & Conditions");
        }
        return null;
    }

    public static StepsHeader steps_header_in(int mCurrentScreen) {
        switch (mCurrentScreen) {
            case 1:
                return new StepsHeader("1", "Data pribadi", "Buat Akun");

            case 2:
                return new StepsHeader("2", "Akun bank", "Buat Akun");

            case 3:
                return new StepsHeader("3", "Data pengguna", "Buat Akun");

            case 4:
                return new StepsHeader("4", "Konfirmasi OTP", "Buat Akun");

            case 5:
                return new StepsHeader("", "", "Syarat & Ketentuan");
        }
        return null;
    }

    public static String getDeviceId(MainDrawerActivity activityObj) {
        String deviceIMEI = Settings.Secure.getString(activityObj.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i(" deviceIMEI: " + deviceIMEI);

        if(deviceIMEI == null){
            WifiManager manager = (WifiManager) activityObj.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            deviceIMEI =  info.getMacAddress();
        }
        Log.i(" deviceIMEI: " + deviceIMEI);
        return  deviceIMEI;
    }

    /*public static String getFormattedAmountWithLBL(String amount) {

        try {


                DecimalFormat df = new DecimalFormat("#.00");
                String temp = df.format(Double.parseDouble(amount));
                if (temp.substring(0, temp.indexOf('.')).trim().length() == 0)
                    temp = "0" + temp;
                String[] arr = temp.split("\\.");
                String first = getFormattedAmount(arr[0]);
                String second = arr[1];
            if (Double.valueOf(amount) >= 1000) {
                String amount1 = first.substring(0,first.length()-4);

                String amount2 = first.substring(first.length() - 4);
                String amount3 = amount2.replace(",",".");

                return "Rp " + amount1 + amount3;
            }
        } catch (Exception e) {
            return "Rp " +amount;
        }

        return "Rp " + amount;
    }*/

    public static String getFormattedAmountWithLBL(String amount) {

        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat df = (DecimalFormat) nf;
            df.applyPattern("##.00");
            // DecimalFormat df = new DecimalFormat("#.00");
            String temp = df.format(Double.parseDouble(amount));
            if (temp.substring(0, temp.indexOf('.')).trim().length() == 0)
                temp = "0" + temp;
            String[] arr = temp.split("\\.");
            String first = getFormattedAmount(arr[0]);
            String second = arr[1];
            if (Double.valueOf(amount) >= 1000) {
                String amount1 = first.substring(0,first.length()-4);
                String amount3 = amount1.replace(",",".");
                String amount2 = first.substring(first.length() - 4);
                String amount4 = amount2.replace(",",".");
                return "Rp " + amount3 + amount4 + "," + second;
            }
            else{
                String amount1 = temp.replace(".",",");
                return "Rp " + amount1;
            }
        } catch (Exception e) {
            return "Rp " +amount;
        }
    }

    public static String getFormattedAmountWithLBLForEdittext(String amount) {

        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat df = (DecimalFormat) nf;
            df.applyPattern("##.00");
            // DecimalFormat df = new DecimalFormat("#.00");
            String temp = df.format(Double.parseDouble(amount));
            if (temp.substring(0, temp.indexOf('.')).trim().length() == 0)
                temp = "0" + temp;
            String[] arr = temp.split("\\.");
            String first = getFormattedAmount(arr[0]);
            String second = arr[1];
            if (Double.valueOf(amount) >= 1000) {
                String amount1 = first.substring(0,first.length()-4);
                String amount3 = amount1.replace(",",".");
                String amount2 = first.substring(first.length() - 4);
                String amount4 = amount2.replace(",",".");
                return  amount3 + amount4 + "," + second;
            }
            else{
                String amount1 = temp.replace(".",",");
                return amount1;
            }
        } catch (Exception e) {
            return amount;
        }
    }

    public static String getFormattedAmount(String amount) {
        try {
            return NumberFormat.getInstance().format(Double.parseDouble(amount));
        } catch (Exception e) {
            return amount;
        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }
    public static String getLangFromSharedPref(MainDrawerActivity activityObj) {
        SharedPreferences preferences = activityObj.getSharedPreferences( activityObj.getPackageName() + "_preferences", MODE_PRIVATE);
        return preferences.getString("Language","");
    }
    public static char[] VALID_CHARACTERS = "123456879".toCharArray();

    public static String csRandomAlphaNumericString(int numChars) {
        SecureRandom srand = new SecureRandom();
        Random rand = new Random();
        char[] buff = new char[numChars];

        for (int i = 0; i < numChars; ++i) {
            if ((i % 10) == 0) {
                rand.setSeed(srand.nextLong());
            }
            buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
        }
        Log.d("Generated password: " + new String(buff));
        return new String(buff);
    }
}
