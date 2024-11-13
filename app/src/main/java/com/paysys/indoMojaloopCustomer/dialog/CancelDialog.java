package com.paysys.indMojaloopCustomer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paysys.indMojaloopCustomer.R;

public class CancelDialog extends CustomDialogFragment {

    private Context context;
    private String details;

    private YesDialogCallback yescallBack;

    private int dlgType = 0;
    private String btnYesText ="";
    private String btnNoText ="";

    boolean changeHeader = false;

    public void setParams(Context context, String details,int type){
        changeHeader = false;
        this.details = details;
        this.context = context;
        this.dlgType = type;
    }

    public void setParams(Context context, String details,int type,String dlgHeader){
        changeHeader = true;
        this.details = details;
        this.context = context;
        this.dlgType = type;
    }

    public void setParams(Context context, String details,int type,String btnNoText, String btnYesText){
        changeHeader = true;
        this.details = details;
        this.context = context;
        this.dlgType = type;
        this.btnNoText = btnNoText;
        this.btnYesText = btnNoText;
    }

    public CancelDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cancel_dialog_layout, container, false);
        getDialog().getWindow().setDimAmount(0.5f);
        initView(view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dlg = super.onCreateDialog(savedInstanceState);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dlg;
    }

    private void initView(View view) {
        ((TextView)view.findViewById(R.id.tv_CanceldlgMsg)).setText(details);

        Button btnYes = view.findViewById(R.id.btn_yesdlg);
        Button btnNo = view.findViewById(R.id.btn_nodlg);

        btnYes.setOnClickListener(new onClickListener());
        btnNo.setOnClickListener(new onClickListener());

    }

    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_yesdlg:
                    yescallBack.yesSelected(dlgType);
                    break;

                case R.id.btn_nodlg:
                    dismiss();
                 break;
        }
        }
    }

    public void hide() {
        if(getDialog()!=null)
            getDialog().dismiss();
    }

    public void setCallback(YesDialogCallback yescallback){

        this.yescallBack = yescallback;
    }

    public interface YesDialogCallback {

        public void yesSelected(int dlgType);
    }

}