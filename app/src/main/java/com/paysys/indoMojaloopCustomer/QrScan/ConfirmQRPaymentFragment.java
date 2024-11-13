package com.paysys.indMojaloopCustomer.QrScan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paysys.indMojaloopCustomer.Fragment.BaseFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.CancelDialog;
import com.paysys.indMojaloopCustomer.dialog.SuccessfulCheckDialog;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.model.QrCode;
import com.paysys.indMojaloopCustomer.model.Request.TitleFetchRequest;
import com.paysys.indMojaloopCustomer.model.Request.TransactionRequest;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TitleFetchResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TransactionResponse;

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
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getFormattedAmountWithLBL;

/**
 * Created by Amsal on 10/25/2019.
 */

public class ConfirmQRPaymentFragment extends BaseFragment implements SuccessfulCheckDialog.OKDialogCallback,CancelDialog.YesDialogCallback{

    View rootView;
    private QrCode qrCode;
    private Call<GenericResponse<TitleFetchResponse>> callback;
    private Call<GenericResponse<TransactionResponse>> callbackTransafer;
    private TextView alias;
    private TitleFetchResponse titleFetchResponse;
    private SuccessfulCheckDialog notifDlg;
    private CancelDialog cancelDlg;
    private TextView reciver_fee;
    private TextView senderFee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.qr_confirm_details_layout, container, false);
        initView(rootView);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(qrCode.getQrType().equals("DYNAMIC"))
            initiateRequestToTitleFetch();
    }

    public  void  initView( View rootView){
        ((DrawerLocker) getActivity()).setToolbar2Visible(true);
        getMainDrawerActivity().hideTabLayout();
        this.rootView  = rootView;
        Button btn_submit = rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new onClickListener());

        alias = rootView.findViewById(R.id.tvMerchantName);
        reciver_fee = rootView.findViewById(R.id.tv_receiverFee);
        senderFee = rootView.findViewById(R.id.tv_senderFee);

        if(qrCode.getQrType().equals("STATIC")) {
            alias.setText(titleFetchResponse.getAccountTitle());
            reciver_fee.setText(getFormattedAmountWithLBL(titleFetchResponse.getFeeAmount()));
            senderFee.setText(String.valueOf(getFormattedAmountWithLBL(titleFetchResponse.getSourceFeeAmount())));
            LinearLayout ll_step_header = rootView.findViewById(R.id.ll_step);
            ll_step_header.setVisibility(View.VISIBLE);
        }


        TextView account = rootView.findViewById(R.id.tvAccount);
        TextView amount = rootView.findViewById(R.id.tvAmount);
        TextView note = rootView.findViewById(R.id.tvNote);
        account.setText(getMainDrawerActivity().inquiryPram.getAccountNumber());
        amount.setText(String.valueOf(getFormattedAmountWithLBL(qrCode.getAmount())));

        note.setText(qrCode.getNote());
    }

    public void setRequestOBj(QrCode qrCode) {
        this.qrCode = qrCode;
    }


    @Override
    public void okSelected(int dlgType) {
        switch (dlgType){
            case 0:
                notifDlg.dismiss();
                getMainDrawerActivity().emptyStackUptil("HomeFragment");
                break;
        }
    }

    public void setRequestOBj(TitleFetchResponse data, QrCode qrCode, String amount, String note) {

    this.titleFetchResponse = data;
    this.qrCode =qrCode;
    this.qrCode.setAmount((amount));
    this.qrCode.setNote(note);
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
                case R.id.btn_submit:
                    initiateRequestForTransfer();
                    break;

            }
        }
    }

    private void initiateRequestToTitleFetch() {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        TitleFetchRequest requestObj = new TitleFetchRequest("SEND","TRANSFER",qrCode.getNote(),
                qrCode.getAliasType(),qrCode.getAmount(),getMainDrawerActivity().inquiryPram.getAlias(),
                getMainDrawerActivity().inquiryPram.getAccountNumber(),
                getMainDrawerActivity().inquiryPram.getInstitutionCode(),
                "IDR",qrCode.getAlias(),
                getMainDrawerActivity().inquiryPram.getAliasType());

        callback = service.titleFetch(requestObj);
        callback.enqueue(new Callback<GenericResponse<TitleFetchResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<TitleFetchResponse>> call, Response<GenericResponse<TitleFetchResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null && response.body().getData() != null && response.body().getData().getToken() != null) {
                    if (response.body().getResponseCode().equals(PROCESSED_OK)) {
                        titleFetchSuccessfulHandler(response.body().getData());
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                            case ACCOUNT_CLOSD:
                                titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
                            case ACCOUNT_INVALID:
                                titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
                                break;
                        }
                        titleFetchUnSuccessfulHandler(response.body().getResponseDescription());

                    }
                }
                else
                    titleFetchUnSuccessfulHandler(response.body().getResponseDescription());
            }
            @Override
            public void onFailure(Call<GenericResponse<TitleFetchResponse>> call, Throwable t) {
                progressDlg.dismiss();
                titleFetchUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }

    private void titleFetchSuccessfulHandler(TitleFetchResponse data) {
        alias.setText(data.getAccountTitle());
        reciver_fee.setText(getFormattedAmountWithLBL(data.getFeeAmount()));
        senderFee.setText(String.valueOf(getFormattedAmountWithLBL(data.getSourceFeeAmount())));
        titleFetchResponse = data;
    }

    private void titleFetchUnSuccessfulHandler(String responseDesc) {
        cancelDlg = new CancelDialog();
        cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 0);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }

    private void initiateRequestForTransfer() {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        TransactionRequest requestObj = new TransactionRequest(titleFetchResponse.getToken(),"MERCHANT", titleFetchResponse.getTransferId());

        callbackTransafer = service.merchantTransfer(requestObj);
        callbackTransafer.enqueue(new Callback<GenericResponse<TransactionResponse>>(){
            @Override
            public void onResponse(Call<GenericResponse<TransactionResponse>> call, Response<GenericResponse<TransactionResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null && response.body().getData() != null) {
                    if (response.body().getResponseCode().equals(PROCESSED_OK)) {
                        validateUserSuccessfulHandler(response.body().getData());
                    } else {
                        switch (response.body().getResponseCode()) {
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                            case ACCOUNT_CLOSD:
                                validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                            case ACCOUNT_INVALID:
                                validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                                break;
                        }
                        validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                    }
                }
                else
                    validateUserUnSuccessfulHandler(response.body().getResponseCode(),response.body().getResponseDescription());
            }
            @Override
            public void onFailure(Call<GenericResponse<TransactionResponse>> call, Throwable t) {
                progressDlg.dismiss();
                validateUserUnSuccessfulFailureHandler(getConnectivityMessage(t));
            }
        });
    }
    private void validateUserUnSuccessfulHandler(String responseCode, String responseDesc) {

        cancelDlg = new CancelDialog();
        if(responseCode.equals("99"))
            cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 0);
        else
            cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 1);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }
    private void validateUserUnSuccessfulFailureHandler(String responseDesc) {

        cancelDlg = new CancelDialog();
        cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 0);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }
    private void validateUserSuccessfulHandler(TransactionResponse responseDesc) {
        notifDlg = new SuccessfulCheckDialog();
        notifDlg.setParams(getMainDrawerActivity(), responseDesc.getTransactionDetails().getDisplayMessage(), 0, "Congratulations!","Return to Menu");
        notifDlg.setCallback(ConfirmQRPaymentFragment.this);
        notifDlg.show(getFragmentManager(), "OkDialog");
    }

    private void onBackButtonPressed() {
        getMainDrawerActivity().popBack();
    }

    @Override
    public void doBack()
    {
        onBackButtonPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
    }

}


