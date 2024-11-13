package com.paysys.indMojaloopCustomer.Fragment.registration;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.crashlytics.android.Crashlytics;
import com.paysys.indMojaloopCustomer.Fragment.BaseFragment;
import com.paysys.indMojaloopCustomer.Fragment.SuccessfulFragment;
import com.paysys.indMojaloopCustomer.Fragment.tncFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.LogoutDialog;
import com.paysys.indMojaloopCustomer.dialog.RegistrationDateSelectionDialog;
import com.paysys.indMojaloopCustomer.dialog.SuccessfulCheckDialog;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.model.Request.GenerateOtpRequest;
import com.paysys.indMojaloopCustomer.model.Request.RegistrationAliasRequest;
import com.paysys.indMojaloopCustomer.model.Request.UsernameAvailabilityRequest;
import com.paysys.indMojaloopCustomer.model.Request.ValidateOTPRequest;
import com.paysys.indMojaloopCustomer.model.Request.ValidateUserRequest;
import com.paysys.indMojaloopCustomer.model.Respose.CreditUnionList;
import com.paysys.indMojaloopCustomer.model.Respose.CreditUnionListRespose;
import com.paysys.indMojaloopCustomer.model.Respose.GenerateOTPResponse;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;
import com.paysys.indMojaloopCustomer.model.Respose.RegistrationAliasResponse;
import com.paysys.indMojaloopCustomer.model.Respose.UsernameAvailabiliyRespose;
import com.paysys.indMojaloopCustomer.model.Respose.ValidateOTPResponse;
import com.paysys.indMojaloopCustomer.model.Respose.ValidateUserResponse;
import com.paysys.indMojaloopCustomer.model.StepsHeader;
import com.paysys.indMojaloopCustomer.model.dropDownList;
import com.paysys.indMojaloopCustomer.utils.Constants;
import com.paysys.indMojaloopCustomer.utils.Log;
import com.paysys.indMojaloopCustomer.utils.UtilMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.paysys.indMojaloopCustomer.utils.Constants.*;
import static com.paysys.indMojaloopCustomer.utils.Constants.MULTI_LOGIN;
import static com.paysys.indMojaloopCustomer.utils.Constants.PROCESSED_OK;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.addLeftTransitonAnimationToView;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.addRightTransitonAnimationToView;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getConnectivityMessage;

public class RegistrationFragment extends BaseFragment implements  RegistrationDateSelectionDialog.DateSelectedCallback, SuccessfulCheckDialog.OKDialogCallback,LogoutDialog.okYesDialogCallback{

    private ViewFlipper viewFlipper;
    private View rootView;
    private TextView stepName, stepCount;
    private TextView registrationHeader;
    private RelativeLayout rela_steps, header;
    private SuccessfulCheckDialog notifDlg;
    private LogoutDialog cancelDlg;

    ///token//
    private String userAvalibiltyToken, validateUserToken;
    /////Step 1////
    private MaterialEditText et_firstName, et_lastName, et_email, et_phoneNumber;
    private String email,phoneNumber, dob, firstname, lastname, fullname;
    String number;
    private String mSelecteDob = "";
    private EditText et_dob;
    private CheckBox Cb_trem_condition;
    /// Step 2//
    private EditText et_Intitution, et_reAccountNumber, et_NewAccount;
    //    private Spinner et_Intitution;
    private String institionCode, reAccountNumber, NewAccount;
    /// Step 3//
    private EditText et_Username, et_reEnterPassword, et_NewPassword,  et_Answer, et_Alias;
    private String username, reEnterpassword, newpassword, secretQuestion, answer, alias,aliasType;
    ////step 4///
    String OTP = "";
    OtpView pinView;
    Button btnConfirmOtp,btn_submit;
    String VerifyId;
    /////step 5////
    TextView btn_trem_contition;
    ImageView backbtn, cancelbtn;

    boolean teem_condation = false;
    ////////// Call /////
    private Call<GenericResponse<UsernameAvailabiliyRespose>> callbackCheckUsername;
    private Call<GenericResponse<ValidateUserResponse>> callbackValidateUser;
    private Call<GenericResponse<RegistrationAliasResponse>> callbackRegistrationAlias;
    private Call<GenericResponse<ValidateOTPResponse>> callbackvalidateOTP;
    private Call<GenericResponse<GenerateOTPResponse>> callbackgenerateOTP;

    private Button step3_btnResend;
    private RegistrationDateSelectionDialog dobSelectionDialog;
    private int mCurrentScreen = 0;
    String[] alisList;

    Spinner sp_institution,sp_SecretQuestion,sp_Alias;
    LinearLayout liner_submit,liner_next,liner_alias,liner_secretQuestion;

    CreditUnionList[] creditUnionLists;
    private String fsp_id;
    private String[] cuInstitutionList;
    private String otp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        initView(rootView);

//        settext();
        return rootView;
    }

    public void initView(View rootView){

        this.rootView = rootView;
        ((DrawerLocker) getActivity()).setDrawerLocked(true);
        mCurrentScreen = 1;
        viewFlipper = rootView.findViewById(R.id.viewFlipper_registerationSteps);

        Button step1_btnContinue = rootView.findViewById(R.id.step1_btnContinue);
        Button step2_btnContinue = rootView.findViewById(R.id.step2_btnContinue);
        Button step3_btncContinue = rootView.findViewById(R.id.step3_btnContinue);
         step3_btnResend = rootView.findViewById(R.id.step3_btnResend);
        btn_trem_contition = rootView.findViewById(R.id.btn_trem_contition);
        rela_steps = rootView.findViewById(R.id.rela_steps);
        header = rootView.findViewById(R.id.header);

        liner_submit = rootView.findViewById(R.id.linerbtn_submit);
        liner_next = rootView.findViewById(R.id.linerbtn_next);
        liner_alias = rootView.findViewById(R.id.liner_alias);
        liner_secretQuestion = rootView.findViewById(R.id.liner_secretQuestion);

        registrationHeader = rootView.findViewById(R.id.registrationHeader);
        stepName = rootView.findViewById(R.id.stepName);
        stepCount = rootView.findViewById(R.id.stepCount);

        ////set 3/////
        et_Username = rootView.findViewById(R.id.et_userName);
        et_NewPassword = rootView.findViewById(R.id.et_newPassword);
        et_reEnterPassword = rootView.findViewById(R.id.et_reEnterPassword);
        sp_SecretQuestion = rootView.findViewById(R.id.sp_secretQuestion);
        sp_Alias = rootView.findViewById(R.id.sp_alias);
        et_Answer = rootView.findViewById(R.id.et_answer);
        et_Alias = rootView.findViewById(R.id.et_alias);
        btn_submit= rootView.findViewById(R.id.step3_btnsubmit);
        ////set 2/////
//        et_Intitution = rootView.findViewById(R.id.et_institution);
        et_NewAccount = rootView.findViewById(R.id.et_accountNumber);
        et_reAccountNumber = rootView.findViewById(R.id.et_reAccountNumber);
        ////set 1/////
        et_firstName = rootView.findViewById(R.id.et_firstName);
//        et_lastName = rootView.findViewById(R.id.et_lastName);
        et_email = rootView.findViewById(R.id.et_email);
        et_phoneNumber = rootView.findViewById(R.id.et_phoneNumber);
        et_dob = rootView.findViewById(R.id.et_dob);
        dobSelectionDialog = new RegistrationDateSelectionDialog();
        dobSelectionDialog.setCallback(this);
        backbtn = rootView.findViewById(R.id.iv_backbtn);
        cancelbtn = rootView.findViewById(R.id.iv_cancelbtn);
        Cb_trem_condition = rootView.findViewById(R.id.Cb_trem_condition);
        ///////setOnClickListener/////////
        btn_trem_contition.setOnClickListener(new onClickListener());
        step2_btnContinue.setOnClickListener(new onClickListener());
        step1_btnContinue.setOnClickListener(new onClickListener());
        step3_btncContinue.setOnClickListener(new onClickListener());
        step3_btnResend.setOnClickListener(new onClickListener());
        et_dob.setOnClickListener(new onClickListener());
        backbtn.setOnClickListener(new onClickListener());
        cancelbtn.setOnClickListener(new onClickListener());
        btn_submit.setOnClickListener(new onClickListener());

        sp_institution = rootView.findViewById(R.id.et_institution);

        sp_institution.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item,
                cuInstitutionList));

        sp_SecretQuestion.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item,
                dropDownList.secretQuestionList));

        sp_Alias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alias = alisList[sp_Alias.getSelectedItemPosition()];
                aliasType =dropDownList.aliasTypelist [sp_Alias.getSelectedItemPosition()];
                if (alias.equals("Other")) {
                    et_Alias.setVisibility(View.VISIBLE);
                }else
                    et_Alias.setVisibility(View.GONE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        et_Username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                feildsNull();
            }
        });
        //////pin view/////////
        btnConfirmOtp = rootView.findViewById(R.id.btnConfirmOTp);
        btnConfirmOtp.setOnClickListener(new onClickListener());
        pinView = rootView.findViewById(R.id.pinView);
        pinView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                OTP = otp;
            }
        });
    }
    private void feildsNull(){
        et_Answer.setText("");
        et_NewPassword.setText("");
        et_reEnterPassword.setText("");
        et_Alias.setText("");
        liner_submit.setVisibility(View.VISIBLE);
        et_Answer.setVisibility(View.GONE);
        liner_secretQuestion.setVisibility(View.GONE);
        liner_alias.setVisibility(View.GONE);
        et_Alias.setVisibility(View.GONE);
        liner_next.setVisibility(View.GONE);

    }
    @Override
    public void okSelected(int dlgType) {
        switch (dlgType){
            case 0:
                notifDlg.dismiss();
                getFragmentManager().popBackStack();
                break;
            case 1:
                notifDlg.dismiss();
                break;
        }
    }


    public void setRequestObj(CreditUnionListRespose cuList) {
        this.creditUnionLists = cuList.getCreditUnionList();
        cuInstitutionList = new String[cuList.getCreditUnionList().length];
        for(int i=0;i< cuList.getCreditUnionList().length;i++){
            cuInstitutionList[i]= cuList.getCreditUnionList()[i].getCu_display();
        }
    }

    @Override
    public void okYesSelected(int dlgType) {
        switch (dlgType){
            case 0:
                cancelDlg.dismiss();
                getMainDrawerActivity().logOutUser();
                break;
            case 1:
                cancelDlg.dismiss();
                break;
        }
    }

    private class onClickListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_backbtn:
                    doBack();
                    break;

                case R.id.step1_btnContinue:
                    credentialSet1();
                    if (isValidateUserSuccess()) {
                        getUserDataStep1();
                        alisList = new String[]{getString(R.string.select_alias), phoneNumber, email, "Other"};
                        sp_Alias.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item,alisList));
                    }
                    break;

                case R.id.step2_btnContinue:
                    institionCode = creditUnionLists[sp_institution.getSelectedItemPosition()].getInstitution_code();
                    fsp_id =  creditUnionLists[sp_institution.getSelectedItemPosition()].getFsp_id();
                    credentialSet2();
                    if (isValidateAccountNumberSuccess())
                        initiateRequestToValidateUser();
                    break;

                case R.id.step3_btnsubmit:
                    credentialSetUser();
                    if(isValidateUsernameSuccess())
                        initiateRequestToCheckUserAvabaility();
                    break;

                case R.id.step3_btnContinue:
                    secretQuestion = dropDownList.secretQuestionList[sp_SecretQuestion.getSelectedItemPosition()];
                    credentialSet3();
                    if (isValidateUserNameSuccess()){
                        initiateRequestToRegistrationAlias(); }
                    break;

                case R.id.btn_trem_contition:
                    getMainDrawerActivity().hideSoftKeyboard();
                    getMainDrawerActivity().addDockableFragment(new tncFragment());
                    teem_condation = true;
                    break;

                case R.id.btnConfirmOTp:
                    if (OTP.isEmpty() || OTP.length() < 6) {
                        Toast.makeText(getActivity(), getString(R.string.invalid_otp), Toast.LENGTH_LONG).show();
                        return;
                    }
                    validateOtp(OTP);
                    //verifyCode(OTP);
                    break;

                case R.id.step3_btnResend:
                    generateOtp();
//                    sendVerificationCode("+9203052121950");
//                    sendVerificationCode(number);
                    break;

                case R.id.iv_cancelbtn:
                    cancelDlg = new LogoutDialog();
                    cancelDlg.setParams(getMainDrawerActivity(), getString(R.string.quit_for_registration), 0);
                    cancelDlg.setCallback(RegistrationFragment.this);
                    cancelDlg.show(getFragmentManager(), "OkDialog");
                    break;
                case R.id.et_dob:
                    if (dobSelectionDialog != null && !dobSelectionDialog.isAdded())
                        dobSelectionDialog.show(getFragmentManager(), "DoB Selection");
                    break;

            }
        }
    }

    private void validateOtp(String OTP) {
        if(otp.equals(OTP))
            initiateRequestToValidateOTP();
        else
            Toast.makeText(getMainDrawerActivity(), "Incorrect OTP provided.", Toast.LENGTH_LONG).show();
    }

    private void generateOtp(){

        otp = UtilMethod.csRandomAlphaNumericString(6);
        initiateRequestToGenerateOTP(otp);
    }

    private void initiateRequestToGenerateOTP(String otp) {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.show();

        GenerateOtpRequest requestObj = new GenerateOtpRequest(getMainDrawerActivity().FcmToken, "Your OTP for registration is :" + otp,"Your OTP for registration is :" + otp,"Mojaloop");

        callbackgenerateOTP = publicService.generateOTP(requestObj);
        callbackgenerateOTP.enqueue(new Callback<GenericResponse<GenerateOTPResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<GenerateOTPResponse>> call, Response<GenericResponse<GenerateOTPResponse>> response) {
                progressDlg.dismiss();
                if (response.body() != null && response.body().getData() != null){
                    if (response.body().getResponseCode().equals(PROCESSED_OK)) {
                        generateOTPSuccessfulHandler();
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                                break;
                        }
                    }
                }else {
                    if (response.body()!=null)
                        generateOTPUnSuccessfulHandler(response.body().getResponseDescription());
                    else
                        generateOTPUnSuccessfulHandler(response.message());
                }}
            @Override
            public void onFailure(Call<GenericResponse<GenerateOTPResponse>> call, Throwable t) {
                progressDlg.dismiss();
                generateOTPUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }

    private void generateOTPUnSuccessfulHandler(String responseDesc) {

        Toast.makeText(getActivity(), getString(R.string.otp_sent_failure), Toast.LENGTH_LONG).show();
    }

    private void generateOTPSuccessfulHandler() {
        btnResendHide();
        Toast.makeText(getActivity(), getString(R.string.otp_sent), Toast.LENGTH_LONG).show();
    }

    ////////////FIRE base call//////////
    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
        btnResendHide();
        Toast.makeText(getActivity(), getString(R.string.otp_sent), Toast.LENGTH_LONG).show();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            try {
                super.onCodeSent(s, forceResendingToken);
                VerifyId = s;
//            RegistrationAliasSuccessfulHandler();
            }catch (Exception e){
                getMainDrawerActivity().mAuth.getCurrentUser().delete();
                Crashlytics.logException(e);
                Toast toast = Toast.makeText(getActivity(), "Something went wrong. Please try again", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinView.setText(code);
//                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            getMainDrawerActivity().mAuth.getCurrentUser().delete();
            Crashlytics.logException(e);
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerifyId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        getMainDrawerActivity().mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            initiateRequestToValidateOTP();
                        } else {
                            Toast.makeText(getMainDrawerActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void dateSelected(String date) {
        dobSelectionDialog.dismiss();

        String formattedDate = date;
        String[] formattedDateArr = date.split("-");
        if(formattedDateArr.length == 3){
            formattedDate = formattedDateArr[2] + "/" + formattedDateArr[1] + "/" + formattedDateArr[0];
        }
        ((TextView)getView().findViewById(R.id.et_dob)).setText(formattedDate);
        mSelecteDob = date;
    }

    ///// Step 1 Validate user call////
    private void initiateRequestToValidateUser(){
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        ValidateUserRequest requestObj = new ValidateUserRequest(reAccountNumber,deviceDetails.getDeviceVersion(),"MOBILE_APP",
                "Customer",getMainDrawerActivity().FcmToken, Constants.str_IMEI, "ANDROID",dob,email,fullname,institionCode,
                "","",phoneNumber,fsp_id);

        callbackValidateUser = publicService.validateUser(requestObj);
        callbackValidateUser.enqueue(new Callback<GenericResponse<ValidateUserResponse>>(){
            @Override
            public void onResponse(Call<GenericResponse<ValidateUserResponse>> call, Response<GenericResponse<ValidateUserResponse>> response) {
                progressDlg.dismiss();
                if (response.body() != null && response.body().getData() != null ) {
                    if (response.body().getResponseCode().equals(PROCESSED_OK)) {
                        validateUserSuccessfulHandler();
                        validateUserToken = response.body().getData().getToken();
                    } else {
                        switch (response.body().getResponseCode()){

                            case ACCOUNT_CLOSD:
                                validateUserUnSuccessfulHandler(response.body().getResponseDescription());
                            case ACCOUNT_INVALID:
                                validateUserUnSuccessfulHandler(response.body().getResponseDescription());
                                break;
                            case SESSION_EXPIRED:
                                Toast.makeText(getActivity(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();
                                break;
                        }
//                    Toast.makeText(getActivity(), "Invalid data", Toast.LENGTH_LONG).show();
                    }
                }else
                if (!response.body().equals(null)) {
                    if (response.body().getResponseCode().equals(SESSION_EXPIRED)) {
                        Toast.makeText(getActivity(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();
                    } else
                        validateUserUnSuccessfulHandler(response.body().getResponseDescription());

                } else
                    validateUserUnSuccessfulHandler(response.message());

            }
            @Override
            public void onFailure(Call<GenericResponse<ValidateUserResponse>> call, Throwable t) {
                progressDlg.dismiss();
                validateUserUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }
    private void validateUserUnSuccessfulHandler(String responseDesc) {
        Toast.makeText(getMainDrawerActivity(),responseDesc , Toast.LENGTH_LONG).show();
    }

    private void validateUserSuccessfulHandler() {
        getMainDrawerActivity().hideSoftKeyboard();
        viewFlipper = addRightTransitonAnimationToView(getActivity(),viewFlipper);
        viewFlipper.showNext();
        cleanAllScreenData();
        mCurrentScreen = 3;
        if (deviceLanguage.toString().equals("in"))
            setHeaderViewFlipper(UtilMethod.steps_header_in(mCurrentScreen));
        else
            setHeaderViewFlipper(UtilMethod.steps_header(mCurrentScreen));
    }
    private void getUserDataStep1(){
        getMainDrawerActivity().hideSoftKeyboard();
        viewFlipper = addRightTransitonAnimationToView(getActivity(),viewFlipper);
        viewFlipper.showNext();
        cleanAllScreenData();
        mCurrentScreen = 2;
        if (deviceLanguage.toString().equals("in"))
            setHeaderViewFlipper(UtilMethod.steps_header_in(mCurrentScreen));
        else
            setHeaderViewFlipper(UtilMethod.steps_header(mCurrentScreen));
    }

    ///////////// Set 2 Check User Avabaility////////////
    private void initiateRequestToCheckUserAvabaility() {

        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        UsernameAvailabilityRequest requestObj = new UsernameAvailabilityRequest
                ("MOBILE_APP","Customer","ANDROID",newpassword,reEnterpassword,validateUserToken,username);
        callbackCheckUsername = publicService.usernameAvailabiliy(requestObj);

        callbackCheckUsername.enqueue(new Callback<GenericResponse<UsernameAvailabiliyRespose>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsernameAvailabiliyRespose>> call, Response<GenericResponse<UsernameAvailabiliyRespose>> response) {
                progressDlg.dismiss();
                if(response.body()!=null || response.body().getData()!= null){
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){
                        userAvalibiltyToken = response.body().getData().getToken();
                        CheckUsernameSuccessfulHandler();
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                                break;
                        }
                        CheckUsernameUnSuccessfulHandler(response.body().getResponseDescription());
                    }
                }else if (!response.body().equals(null))
                    CheckUsernameUnSuccessfulHandler(response.body().getResponseDescription());
                else
                    CheckUsernameUnSuccessfulHandler(response.message());

            }
            @Override
            public void onFailure(Call<GenericResponse<UsernameAvailabiliyRespose>> call, Throwable t) {
                progressDlg.dismiss();
                CheckUsernameUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }
    private void CheckUsernameUnSuccessfulHandler(String responseDesc) {
        Toast.makeText(getMainDrawerActivity(), responseDesc, Toast.LENGTH_LONG).show();

    }
    private void CheckUsernameSuccessfulHandler() {
        getMainDrawerActivity().hideSoftKeyboard();
        liner_submit.setVisibility(View.GONE);
        et_Answer.setVisibility(View.VISIBLE);
        liner_secretQuestion.setVisibility(View.VISIBLE);
        liner_alias.setVisibility(View.VISIBLE);
        liner_next.setVisibility(View.VISIBLE);
    }
    /////Step 4 Registration Alias call////
    private void initiateRequestToRegistrationAlias() {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        RegistrationAliasRequest requestObj = new RegistrationAliasRequest(alias,"ALIAS","IDR",answer,secretQuestion,userAvalibiltyToken);

        callbackRegistrationAlias = publicService.registrationAlias(requestObj);
        callbackRegistrationAlias.enqueue(new Callback<GenericResponse<RegistrationAliasResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<RegistrationAliasResponse>> call, Response<GenericResponse<RegistrationAliasResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null && response.body().getData() != null && response.body().getData().getToken() != null){
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){
                        validateUserToken = response.body().getData().getToken() ;
                        RegistrationAliasSuccessfulHandler();
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                                break;
                        }
                        RegistrationAliasUnSuccessfulHandler(response.body().getResponseDescription());
                    }
                }
                else if (!response.body().equals(null))
                    RegistrationAliasUnSuccessfulHandler(response.body().getResponseDescription());
                else
                    RegistrationAliasUnSuccessfulHandler(response.message());
            }
            @Override
            public void onFailure(Call<GenericResponse<RegistrationAliasResponse>> call, Throwable t) {
                progressDlg.dismiss();
                RegistrationAliasUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }
    private void RegistrationAliasUnSuccessfulHandler(String responseDesc) {
        Toast.makeText(getMainDrawerActivity(),responseDesc , Toast.LENGTH_LONG).show();
    }
    private void RegistrationAliasSuccessfulHandler(){
        getMainDrawerActivity().hideSoftKeyboard();
        generateOtp();
        // sendVerificationCode(number);
        viewFlipper = addRightTransitonAnimationToView(getActivity(),viewFlipper);
        viewFlipper.showNext();
        cleanAllScreenData();
        mCurrentScreen = 4;
        if (deviceLanguage.toString().equals("in"))
            setHeaderViewFlipper(UtilMethod.steps_header_in(mCurrentScreen));
        else
            setHeaderViewFlipper(UtilMethod.steps_header(mCurrentScreen));
    }
    ///// Step 5 Validate Otp call////
    private void initiateRequestToValidateOTP() {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        ValidateOTPRequest requestObj = new ValidateOTPRequest(validateUserToken);

        callbackvalidateOTP = publicService.validateOTP(requestObj);
        callbackvalidateOTP.enqueue(new Callback<GenericResponse<ValidateOTPResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<ValidateOTPResponse>> call, Response<GenericResponse<ValidateOTPResponse>> response) {
                progressDlg.dismiss();
                if (response.body() != null && response.body().getData() != null){
                    if (response.body().getResponseCode().equals(PROCESSED_OK)) {
                        ValidateOTPSuccessfulHandler();
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                                break;
                        }
                    }
                }else {
                    if (!response.body().equals(null))
                        ValidateOTPUnSuccessfulHandler(response.body().getResponseDescription());
                    else
                        ValidateOTPUnSuccessfulHandler(response.message());
                }}
            @Override
            public void onFailure(Call<GenericResponse<ValidateOTPResponse>> call, Throwable t){
                progressDlg.dismiss();
                ValidateOTPUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }

    private void ValidateOTPUnSuccessfulHandler(String responseDesc){
        Toast.makeText(getMainDrawerActivity(),responseDesc , Toast.LENGTH_LONG).show();
        pinView.setText("");
        OTP ="";
    }

    private void ValidateOTPSuccessfulHandler(){

        SuccessfulFragment fragObj = new SuccessfulFragment( getString(R.string.successfull_registeration),"LoginFragment","Login","Congratulations!");
        getMainDrawerActivity().addDockableFragment(fragObj);
        getMainDrawerActivity().hideSoftKeyboard();

      /*  notifDlg = new SuccessfulCheckDialog();
        notifDlg.setParams(getMainDrawerActivity(), getString(R.string.successfull_registeration), 0, "Congratulations!","Login");
        notifDlg.setCallback(RegistrationFragment.this);
        notifDlg.show(getFragmentManager(), "OkDialog");*/

       /* getMainDrawerActivity().hideSoftKeyboard();
        viewFlipper = addRightTransitonAnimationToView(getActivity(),viewFlipper);
        viewFlipper.showNext();
        rela_steps.setVisibility(View.GONE);
        header.setVisibility(View.GONE);*/
    }

    ////////step 1 set credenial///////
    private void credentialSet1() {
        firstname = et_firstName.getText().toString();
//        lastname = et_lastName.getText().toString();
        email = et_email.getText().toString();
        phoneNumber = et_phoneNumber.getText().toString();
        number ="+62"+phoneNumber;
        fullname = firstname;
        dob=mSelecteDob;
    }
    ////////step 2 set credenial///////
    private void credentialSet2() {
//        institionCode = et_Intitution.getText().toString().trim();
        NewAccount = et_NewAccount.getText().toString();
        reAccountNumber = et_reAccountNumber.getText().toString();
    }

    ////////step 2 set credenial///////
    private void credentialSetUser() {
        username = et_Username.getText().toString().trim();
        reEnterpassword = et_reEnterPassword.getText().toString();
        newpassword = et_NewPassword.getText().toString();
    }

    ////////step 3 set credenial///////
    private void credentialSet3() {
        username = et_Username.getText().toString().trim();
        reEnterpassword = et_reEnterPassword.getText().toString();
        newpassword = et_NewPassword.getText().toString();

//        secretQuestion= et_SecretQuestion.getText().toString();
        answer = et_Answer.getText().toString();
//        alias = et_Alias.getText().toString();
        if(alias.equals("Other")){
            alias = et_Alias.getText().toString();
        }else {
            alias = alisList[sp_Alias.getSelectedItemPosition()];

        }

    }
    ////////////////////////step 1 Validate credenial///////
    private boolean isValidateUserSuccess() {
        if(firstname.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.first_name_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!Cb_trem_condition.isChecked()){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.tnc),Toast.LENGTH_LONG).show();
            return  false;
        }
//        else if (lastname.equals("")){
//            Toast.makeText(getMainDrawerActivity()," Last Name cannot be null",Toast.LENGTH_LONG).show();
//            return  false;
//        }
        else if (email.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.email_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if(phoneNumber.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.phone_number),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (dob.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.dob_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }

    ////////////////////////step 2 Validate credenial///////
    private boolean isValidateUsernameSuccess() {
        if (username.equals("") ||newpassword .equals("")|| reEnterpassword.equals("")) {
            Toast.makeText(getMainDrawerActivity(), getString(R.string.invalid_data), Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!newpassword.equals(reEnterpassword)){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.pass_not_match_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
    ////////////////////////step 2 Validate credenial///////
    private boolean isValidateAccountNumberSuccess() {
        if (institionCode.equals("") ||reAccountNumber .equals("")|| NewAccount.equals("")) {
            Toast.makeText(getMainDrawerActivity(),getString(R.string.invalid_data),Toast.LENGTH_LONG).show();
            return  false;
        }
//        if(et_Intitution.getText().length() < 6){
//            Toast.makeText(getMainDrawerActivity(),"Invalid Username Length",Toast.LENGTH_LONG).show();
//            return  false;
//        }
        else if (et_NewAccount.getText().length() < 6){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.account_length_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!NewAccount.equals(reAccountNumber)){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.account_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
    ////////////////////////step 3 Validate credenial///////
    private boolean isValidateUserNameSuccess() {
        if (username.equals("") || newpassword.equals("")|| reEnterpassword.equals(""))
        {
            Toast.makeText(getMainDrawerActivity(),getString(R.string.invalid_user_pass),Toast.LENGTH_LONG).show();
            return  false;
        }
        if(et_Username.getText().length() < 5){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.invalid_user_length),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (et_NewPassword.getText().length() < 6){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.pass_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!newpassword.equals(reEnterpassword)){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.pass_not_match_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (secretQuestion.equals("") || secretQuestion.equals("Select a secret question")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.secret_ques_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (answer.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.secret_ans_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (alias.equals("")|| alias.equals(getString(R.string.select_alias))){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.alias_validation),Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }

    public void settext(){
        et_firstName.setText("I Gusti Ayu Putu Purnamayanthi");
//    et_lastName.setText("Viki");
        et_email.setText("purnamayanti@gmail.com");
        et_NewAccount.setText("022008003000908");
        et_phoneNumber.setText("08818283332");
        et_reAccountNumber.setText("022008003000908");
    }
    private void onBackButtonPressed() {
        mCurrentScreen -= 1 ;
        Log.d(" vf mCurrentScreen " + mCurrentScreen);
        cleanAllScreenData();
        if(mCurrentScreen > 0){
            viewFlipper = addLeftTransitonAnimationToView(getActivity(),viewFlipper);
            viewFlipper.showPrevious();
            if (deviceLanguage.toString().equals("in"))
                setHeaderViewFlipper(UtilMethod.steps_header_in(mCurrentScreen));
            else
                setHeaderViewFlipper(UtilMethod.steps_header(mCurrentScreen));
        }else
            getMainDrawerActivity().popBack();
    }

    private void setHeaderViewFlipper(StepsHeader steps_header) {
        stepName.setText(steps_header.getStepName());
        stepCount.setText(" "+steps_header.getStepCount());
        registrationHeader.setText(steps_header.getRegistrationHeader());
    }

    @Override
    public void doBack(){
        onBackButtonPressed();
    }

    private void  cleanAllScreenData() {
//        et_firstName.setText("");
//        et_phoneNumber.setText("");
//        et_email.setText("");
//        et_dob.setText("");
        et_NewAccount.setText("");
        et_reAccountNumber.setText("");
        institionCode = "";
        et_Username.setText("");
        et_NewPassword.setText("");
        et_reEnterPassword.setText("");
        et_Answer.setText("");
        et_Alias.setText("");
        pinView.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
    }
    private void btnResendHide(){
        int SPLASH_DISPLAY_LENGTH =30000;
        step3_btnResend.setEnabled(false);
        step3_btnResend.setTextColor(Color.parseColor("#f4f6fa"));
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                step3_btnResend.setEnabled(true);
                step3_btnResend.setTextColor(Color.parseColor("#008d8f"));
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
