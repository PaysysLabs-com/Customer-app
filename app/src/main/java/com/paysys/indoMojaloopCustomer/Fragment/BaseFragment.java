package com.paysys.indMojaloopCustomer.Fragment;

import android.os.Bundle;

import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paysys.indMojaloopCustomer.Activity.MainDrawerActivity;
import com.paysys.indMojaloopCustomer.api.MojaLoopApi;
import com.paysys.indMojaloopCustomer.api.MojaLoopPublicApi;
import com.paysys.indMojaloopCustomer.api.MojaloopService;
import com.paysys.indMojaloopCustomer.interfaces.OnBackPressedListener;
import com.paysys.indMojaloopCustomer.utils.Log;


public abstract class BaseFragment extends Fragment implements OnBackPressedListener {

    protected MojaLoopApi service = MojaloopService.getApi();
    protected MojaLoopPublicApi publicService = MojaloopService.getPublicApi();

    public BaseFragment(){
    }

    public MainDrawerActivity getMainDrawerActivity(){
        return (MainDrawerActivity) getActivity();
    }

    @Override
    public void doBack() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Log.d(" basefrag on create");
    }
}
