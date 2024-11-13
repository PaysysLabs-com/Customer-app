package com.paysys.indMojaloopCustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;

public class SuccessfulFragment extends BaseFragment {
    String msg;
    View rootView;
    String message;
    String redrictFragemnt;
    String btntext;
    String header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sucessful_layout, container, false);

        ((DrawerLocker) getActivity()).setDrawerLocked(true);
        getMainDrawerActivity().hideTabLayout();
        ((DrawerLocker) getActivity()).setToolbar2Visible(false);

        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView){
        this.rootView  = rootView;
        TextView tv_dlgHeader = rootView.findViewById(R.id.tv_dlgHeader);
        tv_dlgHeader.setText(header);
        TextView tv_dlgMsg = rootView.findViewById(R.id.tv_Msg);
        tv_dlgMsg.setText(message);
        Button btn_back = rootView.findViewById(R.id.btn_back);
        btn_back.setText(btntext);
        btn_back.setOnClickListener(new SuccessfulFragment.onClickListener());
    }

    public SuccessfulFragment(String message, String redrictFragemnt, String btntext, String header) {
        this.message = message;
        this.redrictFragemnt = redrictFragemnt;
        this.btntext = btntext;
        this.header = header;
    }

    private void onBackButtonPressed() {
        getMainDrawerActivity().emptyStackUptil(redrictFragemnt);
    }

    @Override
    public void doBack() {
        onBackButtonPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
    }

    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.btn_back:
                   getMainDrawerActivity().emptyStackUptil(redrictFragemnt);
                    break;
            }
        }
    }
}


