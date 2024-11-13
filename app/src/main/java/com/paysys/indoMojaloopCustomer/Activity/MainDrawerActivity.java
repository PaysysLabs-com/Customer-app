package com.paysys.indMojaloopCustomer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.paysys.indMojaloopCustomer.Fragment.BaseFragment;
import com.paysys.indMojaloopCustomer.Fragment.ConfirmPaymentFragment;
import com.paysys.indMojaloopCustomer.Fragment.HomeFragment;
import com.paysys.indMojaloopCustomer.Fragment.SplashFragment;
import com.paysys.indMojaloopCustomer.Fragment.TransferFragment;
import com.paysys.indMojaloopCustomer.QrScan.DecoderFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.CancelDialog;
import com.paysys.indMojaloopCustomer.dialog.LogoutDialog;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.interfaces.OnBackPressedListener;
import com.paysys.indMojaloopCustomer.model.SendParams.BalanceInquiryPrram;
import com.paysys.indMojaloopCustomer.utils.Constants;
import com.paysys.indMojaloopCustomer.utils.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paysys.indMojaloopCustomer.utils.UtilMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class MainDrawerActivity extends AppCompatActivity implements DrawerLocker,CancelDialog.YesDialogCallback, LogoutDialog.okYesDialogCallback {

    DrawerLayout drawer;
    Toolbar toolbar, toolbar2;
    public FirebaseAuth mAuth;
    HorizontalScrollView tablayout;
    ImageButton btnLogout;
    AlertDialog.Builder builder1;
    private OnBackPressedListener onBackPressedListener;
    private CancelDialog cancelDlg;
    public String  FcmToken;
    public BalanceInquiryPrram inquiryPram = new BalanceInquiryPrram();
    public TextView accountNumber, drawerUsername, toolbarUsername;
    private LogoutDialog logoutDlg;
    private Locale myLocale;
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mAuth = FirebaseAuth.getInstance();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Crashlytics.getInstance();

        Constants.str_IMEI = UtilMethod.getDeviceId(this);
        Constants.deviceLanguage = UtilMethod.getLangFromSharedPref(this);
        setSupportActionBar(toolbar);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        btnLogout = findViewById(R.id.btnLogout);
        tablayout = findViewById(R.id.tablayout);
        toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setVisibility(View.GONE);
        NavigationView navigationView = findViewById(R.id.nav_view);

        accountNumber = navigationView.getHeaderView(0).findViewById(R.id.tvAccountNumber);
        drawerUsername = navigationView.getHeaderView(0).findViewById(R.id.tvDrawerUsername);

        toolbarUsername = findViewById(R.id.tvToolbarUsername);

        TextView option1 = findViewById(R.id.btnOption1);
        TextView option2 = findViewById(R.id.btnOption2);
        TextView option3 = findViewById(R.id.btnOption3);
        TextView option4 = findViewById(R.id.btnOption4);
        TextView btnP2P = findViewById(R.id.btnP2P);
        ImageView ivBackBtn = findViewById(R.id.iv_backbtn);
        ImageView ivCancelBtn = findViewById(R.id.iv_cancelbtn);

        option1.setOnClickListener(new onClickListener());
        option2.setOnClickListener(new onClickListener());
        option3.setOnClickListener(new onClickListener());
        option4.setOnClickListener(new onClickListener());
        btnP2P.setOnClickListener(new onClickListener());
        ivBackBtn.setOnClickListener(new onClickListener());
        ivCancelBtn.setOnClickListener(new onClickListener());

        btnLogout.setOnClickListener(new onClickListener());
        ////generate Fcm Token/////
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //.makeText(MainDrawerActivity.this, "getInstanceId failed"+ task.getException(), Toast.LENGTH_SHORT).show();
//                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        FcmToken = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//
                       Log.d(FcmToken);
                        //Toast.makeText(MainDrawerActivity.this, FcmToken, Toast.LENGTH_SHORT).show();
                    }
                });
        try {

            Constants.deviceDetails = UtilMethod.getDeviceDetails();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SplashFragment())
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commitAllowingStateLoss();

        } catch (Exception e){
            e.printStackTrace();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        loadLocale1();
//        savedlangInPreferences("in");

        UtilMethod.getFormattedAmountWithLBL("100000.5");
    }

    @Override
    public void yesSelected(int dlgType) {
        switch (dlgType) {
            case 0:
                cancelDlg.dismiss();
                logOutUser();
                break;
        }
    }
    public void removeOnBackPressedListener(){
        this.onBackPressedListener = null;
    }

    @Override
    public void okYesSelected(int dlgType) {
        switch (dlgType) {
            case 0:
                logoutDlg.dismiss();
                tablayout.setVisibility(View.GONE);
                logOutUser();
                break;
        }
    }
    @Override
    protected void onPause() {
        isPaused = true;
        super.onPause();
    }

    public boolean isPaused(){
        return isPaused;
    }

    @Override
    protected void onResume() {
        isPaused = false;
        super.onResume();
    }
    private class onClickListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnOption1:
                    addDockableFragment(new ConfirmPaymentFragment());
                    break;

                case R.id.btnOption2:
                    addDockableFragment(new ConfirmPaymentFragment());
                    break;

                case R.id.btnOption3:
                    addDockableFragment(new ConfirmPaymentFragment());
                    break;

                case R.id.btnP2P:
                    addDockableFragment(new TransferFragment());
                    break;

                case R.id.btnOption4:
                    addDockableFragment(new ConfirmPaymentFragment());
                    break;

                case R.id.btnLogout:
                  logout();
                    break;

                case R.id.iv_backbtn:
                    onBackPressed();
                    break;

                case R.id.iv_cancelbtn:
                    addDockableFragment(new HomeFragment());
                    break;
            }
        }
    }
    public void drawerSectionItems(View view){
        drawer.closeDrawer(GravityCompat.START);
        switch (view.getId()) {

            case R.id.drawer_tvHome:
               addDockableFragment(new HomeFragment());
                break;

            case R.id.drawer_Transfer:
                addDockableFragment(new TransferFragment());
                break;

            case R.id.drawer_tvQrPay:
                addDockableFragment(new DecoderFragment());
                break;

            case R.id.drawer_tvOtherService:
                Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.drawer_tvPayment:
                Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;

            case R.id.drawer_tvLogout:
               logout();
                break;
        }
    }
    public void getTabLayout(){
        tablayout.setVisibility(View.VISIBLE);
    }

    public void hideTabLayout(){
        tablayout.setVisibility(View.GONE);
    }
    public void addDockableFragment(BaseFragment frag ) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        String fragmentName=frag.toString().substring(0, frag.toString().indexOf("{"));
//        navigationControlCheck(fragmentName);
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right).
                addToBackStack(fragmentName).replace(R.id.container, frag).commitAllowingStateLoss();

        logviewbackStack();
    }

    public void logviewbackStack() {

        FragmentManager fm = getSupportFragmentManager();
        for(int i = fm.getBackStackEntryCount()-1; i>= 0; i--){
            Log.v("entry of : " + fm.getBackStackEntryAt(i).getName() + " at " + i);
        }
    }

    public void popBack() {
        FragmentManager fm = getSupportFragmentManager();
        Log.d(" popping back : count :" + fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() - 1 > 0)
            fm.popBackStack();
    }

    public void hideSoftKeyboard() {
        try{
            InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch(Exception e){
        }
    }

    @Override
    public void setDrawerLocked(boolean shouldLock) {
        if(shouldLock){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setVisibility(View.GONE);
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setToolbar2Visible(boolean isVisible) {
        if(isVisible){
            //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setVisibility(View.GONE);
            toolbar2.setVisibility(View.VISIBLE);
        }
        else {
            toolbar2.setVisibility(View.GONE);
        }
    }

    public void logOutUser(){
        emptyStackUptil("LoginFragment");
    }

    public  void logout(){

        logoutDlg = new LogoutDialog();
        logoutDlg.setParams(MainDrawerActivity.this, getString(R.string.logout), 0);
        logoutDlg.setCallback(MainDrawerActivity.this);
        logoutDlg.show(getSupportFragmentManager(), "OkDialog");
    }

    public void emptyStackUptil(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        int i =  fm.getBackStackEntryCount()-1;
        if(i>=0){
            while(!fm.getBackStackEntryAt(i).getName().equals(fragmentName)){
                Log.v("removing : " + fm.getBackStackEntryAt(i).getName());
                fm.popBackStack();
                i--;
            }
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {

        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();

        drawer = findViewById(R.id.drawer_layout);
        int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentCount > 1) {
                getFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void loadLocale1() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void
    savedlangInPreferences(String lang){
        String langPref = "Language";
        SharedPreferences preferences = getSharedPreferences( getPackageName() + "_preferences", MODE_PRIVATE);
        preferences.edit().putString(langPref,lang).commit();
        changeLang(lang);
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

/*    @Override
    public void onBackPressed() {

        int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
        if (fragmentCount == 1) {
            finish();
        } else {
            if (fragmentCount > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

        }
    }*/
}