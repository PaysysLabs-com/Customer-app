package com.paysys.indMojaloopCustomer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.utils.Log;

public class CustomDialogFragment extends DialogFragment {
    protected Context context;
    protected String title;

    protected int suggestedDlgHeight = 0;
    public int heightPixels,widthPixels;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(" Custom onCrateDialog ");
        Dialog dlg = new Dialog(getActivity(), R.style.CustomDialog);
        dlg.setCancelable(false);
        widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;

        suggestedDlgHeight = heightPixels/4;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dlg.getWindow().getAttributes());
        lp.gravity = Gravity.TOP;
        lp.width = widthPixels;
        lp.height = WindowManager.LayoutParams. MATCH_PARENT;
        dlg.getWindow().setAttributes(lp);

        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dlg.getWindow().setDimAmount(0.5f);

        return dlg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @Override
    public void show(FragmentManager manager, String tag) {

        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("Exception");
        }
    }

}
