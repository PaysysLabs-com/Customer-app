package com.paysys.indMojaloopCustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paysys.indMojaloopCustomer.R;

public class ConfirmPaymentFragment extends BaseFragment{

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.confirm_payment_dialogbox, container, false);
        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView){
        this.rootView  = rootView;
//        Button btn_send = rootView.findViewById(R.id.btn_send);
//        btn_send.setOnClickListener(new ConfirmPaymentFragment.onClickListener());

    }

//    private class onClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.btnSaveReceipt:
////                    getMainDrawerActivity().addDockableFragment(new ConfirmPaymentFragment());
//
//                    break;
//
//            }
//        }
//    }
}

