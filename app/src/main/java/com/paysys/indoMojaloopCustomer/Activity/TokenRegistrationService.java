/*
package com.example.mojaloop_app.Activity;

*/
/**
 * Created by Amsal on 5/9/2018.
 *//*


import android.content.SharedPreferences;

import com.example.mojaloop_app.utils.Log;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.Context.MODE_PRIVATE;

public class TokenRegistrationService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // My custom manager
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        android.util.Log.d("xxx","FCMToken:" + refreshedToken);
        Log.d("Refreshed token: " + refreshedToken);
        saveRegistration(refreshedToken);
    }

    private void saveRegistration(final String FCMToken) {
        // Here I can send FCMToken to my db
        SharedPreferences preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);
        preferences.edit().putString("fcm_key",FCMToken).commit();
        Constants.deviceToken = FCMToken ;
    }
}*/
