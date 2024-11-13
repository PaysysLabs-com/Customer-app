package com.paysys.indMojaloopCustomer.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paysys.indMojaloopCustomer.QrScan.DecoderFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.model.Request.BalanceInquiryRequest;
import com.paysys.indMojaloopCustomer.model.Respose.BalanceInquiryResponse;
import com.paysys.indMojaloopCustomer.model.Respose.GenericResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.paysys.indMojaloopCustomer.utils.Constants.MULTI_LOGIN;
import static com.paysys.indMojaloopCustomer.utils.Constants.PROCESSED_OK;
import static com.paysys.indMojaloopCustomer.utils.Constants.SESSION_EXPIRED;
import static com.paysys.indMojaloopCustomer.utils.Constants.SESSION_INVALID;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getConnectivityMessage;
import static com.paysys.indMojaloopCustomer.utils.UtilMethod.getFormattedAmountWithLBL;

public class HomeFragment extends BaseFragment{

    View rootView;
    private TextView tv_amount;
    Button btnTransfer,btnQrPay,btnPayment,btnOtherServices,btnTopUp;
    TextView tv_actualBalance;

    private Call<GenericResponse<BalanceInquiryResponse>> callbackBalanceInquiry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_layout, container, false);

        initView(rootView);
        return rootView;
    }
    public void initView( View rootView) {

        this.rootView = rootView;
        getMainDrawerActivity().hideTabLayout();
        ((DrawerLocker) getActivity()).setDrawerLocked(false);
        ((DrawerLocker) getActivity()).setToolbar2Visible(false);
        getMainDrawerActivity().hideTabLayout();

        tv_amount= rootView.findViewById(R.id.tv_Amount);
        btnTransfer = rootView.findViewById(R.id.btn_transferce);
        btnQrPay= rootView.findViewById(R.id.btn_qr_pay);
        btnPayment = rootView.findViewById(R.id.btn_Payment);
        btnOtherServices= rootView.findViewById(R.id.btn_other_Services);
        btnTopUp= rootView.findViewById(R.id.btn_topup);
        tv_actualBalance= rootView.findViewById(R.id.tv_actualBalance);

        btnTransfer.setOnClickListener(new HomeFragment.onClickListener());
        btnTopUp.setOnClickListener(new HomeFragment.onClickListener());
        btnOtherServices.setOnClickListener(new HomeFragment.onClickListener());
        btnPayment.setOnClickListener(new HomeFragment.onClickListener());
        btnQrPay.setOnClickListener(new HomeFragment.onClickListener());
        getMainDrawerActivity().accountNumber.setText(getMainDrawerActivity().inquiryPram.getAccountNumber());
        getMainDrawerActivity().drawerUsername.setText(getMainDrawerActivity().inquiryPram.getUsername());
        getMainDrawerActivity().toolbarUsername.setText(getMainDrawerActivity().inquiryPram.getUsername());

/////call for balance Inqueiry//////
        initiateRequestforBalanceInquiry();
    }
    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_transferce:
                   getMainDrawerActivity().addDockableFragment(new TransferFragment());
                    break;
                case R.id.btn_qr_pay:
                    getMainDrawerActivity().addDockableFragment(new DecoderFragment());
                    break;
                case R.id.btn_Payment:
                    Toast.makeText(getActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_other_Services:
                    Toast.makeText(getActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_topup:
                    Toast.makeText(getActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    ///////////// Set 3 Check User Avabaility////////////
    private void initiateRequestforBalanceInquiry(){

        final ProgressDialog progressDlg = new ProgressDialog(getMainDrawerActivity());
        progressDlg.setMessage(getString(R.string.loading));
        progressDlg.setCancelable(false);
        progressDlg.show();

        final BalanceInquiryRequest requestObj = new BalanceInquiryRequest
                (getMainDrawerActivity().inquiryPram.getInstitutionCode(),getMainDrawerActivity().inquiryPram.getAccountNumber());

        callbackBalanceInquiry = service.balanceInquiry(requestObj);

        callbackBalanceInquiry.enqueue(new Callback<GenericResponse<BalanceInquiryResponse>>(){
            @Override
            public void onResponse(Call<GenericResponse<BalanceInquiryResponse>> call, Response<GenericResponse<BalanceInquiryResponse>> response) {
                progressDlg.dismiss();
                if(response.body()!=null || response.body().getData()!= null)
                    if(response.body().getResponseCode().equals(PROCESSED_OK)){
                        getMainDrawerActivity().inquiryPram.setActualBalance(response.body().getData().getAvailableBalance());
                        tv_actualBalance.setText(String.valueOf(getFormattedAmountWithLBL(response.body().getData().getActualBalance())));
                        tv_amount.setText(String.valueOf(getFormattedAmountWithLBL(response.body().getData().getAvailableBalance())));
                    } else {
                        switch (response.body().getResponseCode()){
                            case SESSION_EXPIRED:
                            case MULTI_LOGIN:
                            case SESSION_INVALID:
                                getMainDrawerActivity().logOutUser();
                                break;
                        }
                        CheckUsernameUnSuccessfulHandler(response.body().getResponseDescription());
                    }
            }
            @Override
            public void onFailure(Call<GenericResponse<BalanceInquiryResponse>> call, Throwable t) {
                progressDlg.dismiss();
                CheckUsernameUnSuccessfulHandler(getConnectivityMessage(t));
            }
        });
    }
    private void CheckUsernameUnSuccessfulHandler(String responseDesc) {
        Toast.makeText(getMainDrawerActivity(), responseDesc, Toast.LENGTH_SHORT).show();
    }

    private void onBackButtonPressed() {
        getMainDrawerActivity().logout();
    }

    @Override
    public void doBack() {
        onBackButtonPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
    }

}
