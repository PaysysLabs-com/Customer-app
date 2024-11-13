package com.paysys.indMojaloopCustomer.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.paysys.indMojaloopCustomer.R;

public class tncFragment extends BaseFragment{

    View rootView;
    private TextView tvEnglishtab, tvFilipinotab;
    private View viewEnglish, viewFilipion;
    ImageView iv_cancelbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.tnc_fragment, container, false);

        initView(rootView);
        return rootView;
    }

    public  void  initView( View rootView) {
        this.rootView = rootView;

        tvEnglishtab = rootView.findViewById(R.id.tvEnglishtab);
        tvFilipinotab = rootView.findViewById(R.id.tvFilipinotab);
        viewEnglish = rootView.findViewById(R.id.viewEnglish);
        viewFilipion = rootView.findViewById(R.id.viewFilipion);
        iv_cancelbtn = rootView.findViewById(R.id.iv_tncCancelBtn);

        tvEnglishtab.setOnClickListener(new tncFragment.onClickListener());
        tvFilipinotab.setOnClickListener(new tncFragment.onClickListener());
        iv_cancelbtn.setOnClickListener(new tncFragment.onClickListener());

    }
    private class onClickListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tvEnglishtab:
                    viewFilipion.setVisibility(rootView.GONE);
                    viewEnglish.setVisibility(rootView.VISIBLE);
                    break;

                case R.id.tvFilipinotab:
                    viewFilipion.setVisibility(rootView.VISIBLE);
                    viewEnglish.setVisibility(rootView.GONE);
                    break;

                case R.id.iv_tncCancelBtn:
                    getFragmentManager().popBackStack();
                break;
            }
        }
    }
}

