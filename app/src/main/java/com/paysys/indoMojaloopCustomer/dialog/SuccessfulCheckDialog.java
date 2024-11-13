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

public class SuccessfulCheckDialog extends CustomDialogFragment {

    private Context context;
    private String details;

    private OKDialogCallback callBack;
    private int dlgType = 0;
    private String dlgHeader = "";
    private String btnText ="";
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
        this.dlgHeader = dlgHeader;
    }

    public void setParams(Context context, String details,int type, String dlgHeader, String btnText){
        changeHeader = true;
        this.details = details;
        this.context = context;
        this.dlgType = type;
        this.dlgHeader = dlgHeader;
        this.btnText = btnText;
    }

    public SuccessfulCheckDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.successful_check_dialog, container, false);
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
        ((TextView)view.findViewById(R.id.tv_dlgMsg)).setText(details);
        if(changeHeader)
            ((TextView)view.findViewById(R.id.tv_dlgHeader)).setText(dlgHeader);

        Button btnOk = (Button) view.findViewById(R.id.btn_dlg);

        if(!btnText.equals(""))
            btnOk.setText(btnText);
        btnOk.setOnClickListener(new onClickListener());

    }



    private class onClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_dlg:
                    callBack.okSelected(dlgType);
                    break;
            }
        }
    }


    public void hide() {
        if(getDialog()!=null)
            getDialog().dismiss();
    }

    public void setCallback(OKDialogCallback callback){

        this.callBack = callback;
    }

    public interface OKDialogCallback {

        public void okSelected(int dlgType);
    }


}