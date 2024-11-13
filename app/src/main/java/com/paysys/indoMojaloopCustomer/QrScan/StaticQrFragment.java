package com.paysys.indMojaloopCustomer.QrScan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paysys.indMojaloopCustomer.Fragment.BaseFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.dialog.CancelDialog;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.model.QrCode;
import com.paysys.indMojaloopCustomer.model.Request.TitleFetchRequest;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;
import com.paysys.indMojaloopCustomer.model.Respose.TitleFetchResponse;
import com.rengwuxian.materialedittext.MaterialEditText;

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

/**
 * Created by Amsal on 10/25/2019.
 */

public class StaticQrFragment extends BaseFragment implements CancelDialog.YesDialogCallback{

    View rootView;
    TextView alias;
    private QrCode qrCode;
    private MaterialEditText amount , reamount, note;
    private Call<GenericResponse<TitleFetchResponse>> callback;
    private TitleFetchResponse titleFetchResponse;
    private CancelDialog cancelDlg;
    String stramount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.static_qr_layout, container, false);
        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView){
        ((DrawerLocker) getActivity()).setToolbar2Visible(true);
        getMainDrawerActivity().hideTabLayout();
        this.rootView  = rootView;
        Button btn_continue = rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new StaticQrFragment.onClickListener());

        alias = rootView.findViewById(R.id.tv_merchant);
        TextView account = rootView.findViewById(R.id.tv_Account);
        amount = rootView.findViewById(R.id.et_amount);
        reamount = rootView.findViewById(R.id.et_re_amount);
        note = rootView.findViewById(R.id.et_notes);
        account.setText(getMainDrawerActivity().inquiryPram.getAccountNumber());
        alias.setText(qrCode.getAlias());

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

        reamount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
//                    Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    String amount1 = reamount.getText().toString().replace(".","");
                    String[] arr = amount1.split(",");
                    String first = arr[0];
                    reamount.setText(getFormattedAmountWithLBLForEdittext(first));
                }
            }
        });
    }

    public void setRequestOBj(QrCode qrCode) {
        this.qrCode= qrCode;
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
                    if(isValidateSuccess())
                        initiateRequestToTitleFetch();
                    break;
            }
        }
    }

    private boolean isValidateSuccess() {
        String amount1 = amount.getText().toString().replace(".","");
        String[] arr = amount1.split(",");
        String reamount = arr[0];
        if (amount.getText().toString().equals("") ||reamount.equals("")) {
            Toast.makeText(getMainDrawerActivity(),getString(R.string.amount_validation),Toast.LENGTH_SHORT).show();
            return  false;
        }

        else if (!stramount.equals(reamount)){
            Toast.makeText(getMainDrawerActivity(),getString(R.string.re_amount_validation),Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    private void initiateRequestToTitleFetch() {
        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        final TitleFetchRequest requestObj = new TitleFetchRequest("SEND","TRANSFER",note.getText().toString(),
                qrCode.getAliasType(),stramount,getMainDrawerActivity().inquiryPram.getAlias(),
                getMainDrawerActivity().inquiryPram.getAccountNumber(),
                getMainDrawerActivity().inquiryPram.getInstitutionCode(),
                "IDR",qrCode.getAlias(),
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
                        Toast.makeText(getActivity(),response.body().getResponseDescription(), Toast.LENGTH_SHORT).show();
                    } else
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
        titleFetchResponse = data;
        ConfirmQRPaymentFragment fragObj = new ConfirmQRPaymentFragment();
        fragObj.setRequestOBj(data,qrCode,amount.getText().toString(),note.getText().toString());
        getMainDrawerActivity().addDockableFragment(fragObj);
    }

    private void titleFetchUnSuccessfulHandler(String responseDesc) {
        cancelDlg = new CancelDialog();
        cancelDlg.setParams(getMainDrawerActivity(), responseDesc, 1);
        cancelDlg.setCallback(this);
        cancelDlg.show(getFragmentManager(), "OkDialog");
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