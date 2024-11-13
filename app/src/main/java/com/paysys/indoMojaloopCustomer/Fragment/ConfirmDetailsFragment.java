package com.paysys.indMojaloopCustomer.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.CancelDialog;
import com.paysys.indMojaloopCustomer.dialog.SuccessfulCheckDialog;
import com.paysys.indMojaloopCustomer.model.Request.AddBanificiaryResponse;
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

public class ConfirmDetailsFragment extends BaseFragment implements   SuccessfulCheckDialog.OKDialogCallback,CancelDialog.YesDialogCallback {

    View rootView;
    private SuccessfulCheckDialog notifDlg;
    private Call<GenericResponse<AddBanificiaryResponse>> AddBanificiaryCallBack;
    private TitleFetchResponse titleFetchResponse;
    private String amount, receiverAlias, note;
    private Call<GenericResponse<TransactionResponse>> callback;
    private CancelDialog cancelDlg;
    private String responseCode;
    ImageView iv_check;
    Button btn_addToPayee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.confirm_details_layout, container, false);
        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView){
        this.rootView  = rootView;
        getMainDrawerActivity().hideTabLayout();
        Button btn_sendDetails = rootView.findViewById(R.id.btn_sendDetails);
        iv_check = rootView.findViewById(R.id.iv_check);

        btn_addToPayee = rootView.findViewById(R.id.btnAddToPayee);

        btn_sendDetails.setOnClickListener(new ConfirmDetailsFragment.onClickListener());
        btn_addToPayee.setOnClickListener(new ConfirmDetailsFragment.onClickListener());

        TextView tvFromAccount = rootView.findViewById(R.id.tvFromAccount);
        tvFromAccount.setText(getMainDrawerActivity().inquiryPram.getAccountNumber());
        
        TextView amount = rootView.findViewById(R.id.tv_amount);
        amount.setText(String.valueOf(getFormattedAmountWithLBL(this.amount)));
        
        TextView alias = rootView.findViewById(R.id.tv_recieve_alias);
        alias.setText(this.receiverAlias);

        TextView name = rootView.findViewById(R.id.tv_recieve_name);
        name.setText( titleFetchResponse.getAccountTitle());

        TextView note = rootView.findViewById(R.id.tvPurposeOfPayment);
        note.setText(this. note);

        TextView senderFee = rootView.findViewById(R.id.tv_senderFee);
        senderFee.setText(String.valueOf(getFormattedAmountWithLBL(titleFetchResponse.getSourceFeeAmount())));

        TextView receiverFee = rootView.findViewById(R.id.tv_receiverFee);
        receiverFee.setText(String.valueOf(getFormattedAmountWithLBL(titleFetchResponse.getFeeAmount())));


    }

    @Override
    public void okSelected(int dlgType) {
        switch (dlgType){
            case 0:
                notifDlg.dismiss();
                getMainDrawerActivity().emptyStackUptil("HomeFragment");
                getMainDrawerActivity().hideTabLayout();
                break;
            case 1:
                notifDlg.dismiss();
                break;
        }
    }

    public void setRequestOBj(TitleFetchResponse data, String receiverAlias, String amount, String note) {
        this.titleFetchResponse = data;
        this.receiverAlias =receiverAlias;
        this.amount = amount;
        this.note = note;
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
                case R.id.btn_sendDetails:
                    initiateRequestForTransfer();

                    break;
                case R.id.btnAddToPayee:
                    Toast.makeText(getActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

    private void initiateRequestForTransfer(){
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage("Proceeding for payment...");
        progressDlg.setCancelable(false);
        progressDlg.show();

        TransactionRequest requestObj = new TransactionRequest(titleFetchResponse.getToken(),"P2P_DEBIT", titleFetchResponse.getTransferId());

        callback = service.p2pTransfer(requestObj);
        callback.enqueue(new Callback<GenericResponse<TransactionResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<TransactionResponse>> call, Response<GenericResponse<TransactionResponse>> response) {
                progressDlg.dismiss();
                responseCode = response.body().getResponseCode();
                if(response.body()!= null && response.body().getData()!= null)
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){
                        validateUserSuccessfulHandler(response.body().getData());
                    }else{
                        switch (response.body().getResponseCode()){
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                            case ACCOUNT_CLOSD:
                            case ACCOUNT_INVALID:
                                validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                                break;
                        }
                        validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                    } else
                if (response.body()!= null)
                    validateUserUnSuccessfulHandler(response.body().getResponseCode(), response.body().getResponseDescription());
                else
                    validateUserUnSuccessfulFaliureHandler(response.message());
            }
            @Override
            public void onFailure(Call<GenericResponse<TransactionResponse>> call, Throwable t) {
                progressDlg.dismiss();
                validateUserUnSuccessfulFaliureHandler(getConnectivityMessage(t));
            }
        });
    }

    private void validateUserUnSuccessfulFaliureHandler(String message) {
        cancelDlg = new CancelDialog();
        cancelDlg.setParams(getMainDrawerActivity(), message, 1);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }

    private void validateUserUnSuccessfulHandler(String responseCode, String responseDesc){
        cancelDlg = new CancelDialog();
        if(responseCode.equals("99"))
            cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 0);
        else {
            cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 1);
        }
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
    }
    private void validateUserSuccessfulHandler(TransactionResponse responseDesc){

       /* String message = getString(R.string.msg_part1)+" \n ₱ "+responseDesc.getTransactionDetails().()+
                " "+getString(R.string.msg_part2)+" "+"\'" + responseDesc.getTransactionDetails().getAlias() + "\'"+"."+
                "\n "+getString(R.string.msg_part3)+" \n ₱ "+responseDesc.getTransactionDetails().getFees();
*/
        SuccessfulFragment fragObj = new SuccessfulFragment(responseDesc.getTransactionDetails().getDisplayMessage(),"HomeFragment","Return to Menu","Congratulations!");
        getMainDrawerActivity().addDockableFragment(fragObj);
     /*   SucessfulFragment fragObj = new SucessfulFragment();
        fragObj.setObj(responseDesc);
        getMainDrawerActivity().addDockableFragment(fragObj);*/
     /*   notifDlg = new SuccessfulCheckDialog();
        notifDlg.setParams(getMainDrawerActivity(), responseDesc.getTransactionDetails().getDisplayMessage(), 0, "Congratulations!","Return to Menu");
        notifDlg.setCallback(ConfirmDetailsFragment.this);
        notifDlg.show(getFragmentManager(), "OkDialog");*/
    }

    private void onBackButtonPressed(){
        if(responseCode != null)
            if(!responseCode.equals(PROCESSED_OK))
                getMainDrawerActivity().emptyStackUptil("HomeFragment");

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
