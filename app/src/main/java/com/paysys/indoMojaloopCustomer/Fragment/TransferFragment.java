package com.paysys.indMojaloopCustomer.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.CancelDialog;
import com.paysys.indMojaloopCustomer.model.Request.TitleFetchRequest;
import com.paysys.indMojaloopCustomer.model.Respose.BeneficiaryDetailsListResponse;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TitleFetchResponse;
import com.paysys.indMojaloopCustomer.utils.CurrencyEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.paysys.indMojaloopCustomer.utils.Constants.ACCOUNT_CLOSD;
import static com.paysys.indMojaloopCustomer.utils.Constants.ACCOUNT_INVALID;
import static com.paysys.indMojaloopCustomer.utils.Constants.MULTI_LOGIN;
import static com.paysys.indMojaloopCustomer.utils.Constants.PROCESSED_OK;
import static com.paysys.indMojaloopCustomer.utils.Constants.SESSION_EXPIRED;
import static com.paysys.indMojaloopCustomer.utils.Constants.SESSION_INVALID;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getConnectivityMessage;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getFormattedAmountWithLBLForEdittext;

public class TransferFragment extends BaseFragment implements CancelDialog.YesDialogCallback{

    View rootView;
    private EditText amount, reamount, note, receiverAlias;
    private Call<GenericResponse<TitleFetchResponse>> callback;
    private Call<GenericResponse<BeneficiaryDetailsListResponse>> BeneficiaryDetailsListCallback;
    private TitleFetchResponse titleFetchResponse;
    private CurrencyEditText etInput;
    private String current = "";
    private CancelDialog cancelDlg;
    //    Spinner sp_Alias;
//    String aliasType;
    String strActualBalance;
    String stramount;
    TextView headerTextReciver;
    LinearLayout linerReciver,liner_alias,liner_rb;
    RadioButton rb_other_alias,rb_existing_alias;
    Button btn_continue;
    Spinner sp_existingAlias;
    String reciverAlias;
    Boolean isOtherSelect = true;
    private String[] existingAliasList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.p2p_transfer_layout, container, false);
        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView){
        getMainDrawerActivity().getTabLayout();
        this.rootView  = rootView;
        liner_alias= rootView.findViewById(R.id.liner_alias);
        btn_continue = rootView.findViewById(R.id.btn_continue);
        liner_rb = rootView.findViewById(R.id.liner_rb);
        Button btn_continue = rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new TransferFragment.onClickListener());
        receiverAlias = rootView.findViewById(R.id.et_Receiver_Alias);
        headerTextReciver = rootView.findViewById(R.id.headerTextReciver);
        linerReciver = rootView.findViewById(R.id.linerReciver);
        rb_other_alias = rootView.findViewById(R.id.rb_other_alias);
        rb_existing_alias = rootView.findViewById(R.id.rb_existing_alias);
        rb_other_alias.setOnClickListener(new TransferFragment.onClickListener());
        rb_existing_alias.setOnClickListener(new TransferFragment.onClickListener());
        TextView account = rootView.findViewById(R.id.tv_Account);
        amount = rootView.findViewById(R.id.et_Amount);
        note = rootView.findViewById(R.id.et_Receiver_Notes);
        account.setText(getMainDrawerActivity().inquiryPram.getAccountNumber());
        initiateRequestToBeneficiaryDetailsList();
        sp_existingAlias= rootView.findViewById(R.id.sp_existingAlias);

        sp_existingAlias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                reciverAlias = existingAliasList[sp_existingAlias.getSelectedItemPosition()];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        try {

            String strBalance =(getMainDrawerActivity().inquiryPram.getActualBalance().replace(".",""));
            String[] arr = strBalance.split(",");
            String first = arr[0];
            strActualBalance = first;

        }catch (Exception e){
            e.printStackTrace();
        }

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
//                    Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    String amount1 = amount.getText().toString().replace(".","");
                    String[] arr = amount1.split(",");
                    String first = arr[0];
                    amount.setText(getFormattedAmountWithLBLForEdittext(first));

                }
            }
        });
    }

    @Override
    public void yesSelected(int dlgType) {
        switch (dlgType) {
            case 0:
                cancelDlg.dismiss();
                doBack();
                break;
            case 1:
                cancelDlg.dismiss();
                break;
        }
    }

    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_continue:
                    String amount1 = amount.getText().toString().replace(".","");
                    String[] arr = amount1.split(",");
                    String first = arr[0];
                    stramount =first;
                    if(isOtherSelect == true){
                        reciverAlias = receiverAlias.getText().toString();
                    }else {
                        reciverAlias = existingAliasList[sp_existingAlias.getSelectedItemPosition()];
                    }
//                     aliasType = dropDownList.TransferAliasTypelist[sp_Alias.getSelectedItemPosition()];
                    if(isValidateSuccess())
                        initiateRequestToTitleFetch();
                    break;

                case R.id.rb_existing_alias:
                    linerReciver.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.VISIBLE);
                    liner_alias.setVisibility(View.VISIBLE);
                    note.setVisibility(View.VISIBLE);
                    receiverAlias.setVisibility(View.GONE);
                    isOtherSelect = false;
                    break;

                case R.id.rb_other_alias:
                    linerReciver.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.VISIBLE);
                    receiverAlias.setVisibility(View.VISIBLE);
                    note.setVisibility(View.VISIBLE);
                    liner_alias.setVisibility(View.GONE);
                    isOtherSelect = true;
                    break;
            }
        }
    }

    private boolean isValidateSuccess() {
        if (amount.getText().toString().equals("")) {
            Toast.makeText(getMainDrawerActivity(),getString(R.string.amount_validation),Toast.LENGTH_SHORT).show();
            return  false;
        }

        else if (reciverAlias.equals("")){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.alias_validation),Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if (Integer.parseInt(stramount) >= Integer.parseInt(strActualBalance)){
            Toast.makeText(getMainDrawerActivity(),"insufficient balance ",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if ( reciverAlias.equals("Select Alias")){
            Toast.makeText(getMainDrawerActivity(), getString(R.string.alias_validation),Toast.LENGTH_SHORT).show();
            return  false;
        }
//        else if(aliasType.equals("")){
//            Toast.makeText(getMainDrawerActivity(),"Amount doesn't match the re-enter amount",Toast.LENGTH_SHORT).show();
//            return  false;
//        }
        return true;
    }

    private void initiateRequestToTitleFetch(){
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.titelloading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        TitleFetchRequest requestObj = new TitleFetchRequest("SEND","TRANSFER",note.getText().toString(),
                "ALIAS",stramount,getMainDrawerActivity().inquiryPram.getAlias(),
                getMainDrawerActivity().inquiryPram.getAccountNumber(),
                getMainDrawerActivity().inquiryPram.getInstitutionCode(),
                "IDR",reciverAlias,
                getMainDrawerActivity().inquiryPram.getAliasType());

        callback = service.titleFetch(requestObj);
        callback.enqueue(new Callback<GenericResponse<TitleFetchResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<TitleFetchResponse>> call, Response<GenericResponse<TitleFetchResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null && response.body().getData() != null && response.body().getData().getToken() != null)
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){
                        titleFetchSuccessfulHandler(response.body().getData());
                    } else{
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                                getMainDrawerActivity().logOutUser();
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                            case ACCOUNT_CLOSD:
                                titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
                            case ACCOUNT_INVALID:
                                titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
                                break;
                        }
                        Toast.makeText(getActivity(), getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                    }
                else if (response.body() != null)
                    titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
                else
                    titleFetchUnSuccessfulHandler(response.message());
            }
            @Override
            public void onFailure(Call<GenericResponse<TitleFetchResponse>> call, Throwable t) {
                progressDlg.dismiss();
                titleFetchUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }

    private void titleFetchSuccessfulHandler(TitleFetchResponse data) {
        titleFetchResponse = data;
        ConfirmDetailsFragment fragObj = new ConfirmDetailsFragment();
        fragObj.setRequestOBj(data,receiverAlias.getText().toString(),amount.getText().toString(),note.getText().toString());
        getMainDrawerActivity().addDockableFragment(fragObj);

    }

    private void titleFetchUnSuccessfulHandler(String responseDesc) {
        cancelDlg = new CancelDialog();
        cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 0);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }
/////////////////////getBeneficiaryList//////////////////////

    private void initiateRequestToBeneficiaryDetailsList(){
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

//        AddBeneficiaryRequest requestObj = new AddBeneficiaryRequest("","");

        BeneficiaryDetailsListCallback = service.getbeneficiaryList();
        BeneficiaryDetailsListCallback.enqueue(new Callback<GenericResponse<BeneficiaryDetailsListResponse>>(){
            @Override
            public void onResponse(Call<GenericResponse<BeneficiaryDetailsListResponse>> call, Response<GenericResponse<BeneficiaryDetailsListResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null && response.body().getData() != null )
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){

                        BeneficiaryDetailsListSuccessfulHandler(response.body().getData());

                    } else{
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                                getMainDrawerActivity().logOutUser();
                            case MULTI_LOGIN:
                                getMainDrawerActivity().logOutUser();
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                            case ACCOUNT_CLOSD:
                                BeneficiaryDetailsListUnSuccessfulHandler(response.body().getResponseDescription());
                            case ACCOUNT_INVALID:
                                BeneficiaryDetailsListUnSuccessfulHandler(response.body().getResponseDescription());
                                break;
                        }
                        Toast.makeText(getActivity(), response.body().getResponseDescription(), Toast.LENGTH_SHORT).show();
                    }
                else {
                    if (response.body() != null)
                        BeneficiaryDetailsListUnSuccessfulHandler(response.body().getResponseDescription());
                    else
                        BeneficiaryDetailsListUnSuccessfulHandler(response.message());
                }
            }
            @Override
            public void onFailure(Call<GenericResponse<BeneficiaryDetailsListResponse>> call, Throwable t) {
                progressDlg.dismiss();
                BeneficiaryDetailsListUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }

    private void BeneficiaryDetailsListSuccessfulHandler(BeneficiaryDetailsListResponse data){
        existingAliasList = new String[data.getBeneficiaryDetailsList().length +1] ;
        //existingAliasList[0] = "Select Alias";

        for(int i=0;i< data.getBeneficiaryDetailsList().length +1 ;i++){
            if (i == 0){
                existingAliasList[i] = "Select Alias";
            }
            else
                existingAliasList[i]= data.getBeneficiaryDetailsList()[i -1].getAlias();

            if (existingAliasList.length != 0) {
                sp_existingAlias.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, existingAliasList));
            }
        }
    }

    private void BeneficiaryDetailsListUnSuccessfulHandler(String responseDesc) {
        linerReciver.setVisibility(View.VISIBLE);
        btn_continue.setVisibility(View.VISIBLE);
        receiverAlias.setVisibility(View.VISIBLE);
        note.setVisibility(View.VISIBLE);
        liner_alias.setVisibility(View.GONE);
        liner_rb.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
    }

    private void onBackButtonPressed() {
        getMainDrawerActivity().emptyStackUptil("HomeFragment");
    }

    @Override
    public void doBack() {
        onBackButtonPressed();
    }

}