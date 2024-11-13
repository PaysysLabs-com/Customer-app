package com.paysys.indMojaloopCustomer.QrScan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.paysys.indMojaloopCustomer.Fragment.BaseFragment;
import com.paysys.indMojaloopCustomer.R;
import com.paysys.indMojaloopCustomer.interfaces.DrawerLocker;
import com.paysys.indMojaloopCustomer.model.PointsOverlayView;
import com.paysys.indMojaloopCustomer.model.QrCode;
import com.paysys.indMojaloopCustomer.utils.Log;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Amsal on 10/25/2019.
 */

public class DecoderFragment extends BaseFragment implements QRCodeReaderView.OnQRCodeReadListener {
    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    private View mainLayout;

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;
    public String amount;
    private View rootView;
    private String tokenID;
    private boolean isQRSucess =false;

    public DecoderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_decoder, container, false);
        ((DrawerLocker)getActivity()).setDrawerLocked(true);
        mainLayout = getActivity().findViewById(android.R.id.content);
        if (ActivityCompat.checkSelfPermission(getMainDrawerActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
        return rootView;
    }

    private void initQRCodeReaderView() {

        qrCodeReaderView = (QRCodeReaderView) rootView.findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) rootView.findViewById(R.id.result_text_view);
        flashlightCheckBox = (CheckBox) rootView.findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = (CheckBox) rootView.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = (PointsOverlayView) rootView.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });
        enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setQRDecodingEnabled(isChecked);
            }
        });
        isQRSucess = false;
        qrCodeReaderView.startCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        try {
            //do here
            QrCode qrCode = qrCodeDecompiler(text);

            resultTextView.setText(text);
            pointsOverlayView.setPoints(points);
            if(!isQRSucess)
                successRead(qrCode);
        } catch (Exception e) {
            Toast.makeText(getMainDrawerActivity(), "Incorrect QR code.", Toast.LENGTH_SHORT).show();
        }
    }

    private QrCode qrCodeDecompiler(String data) {
        String currentString = data;
        QrCode qrCode = null;
        String[] qrType = currentString.split("/00");
        String[] alias = qrType[1].trim().split("/01");
        String[] aliasType = alias[1].trim().split("/02");
        String[] amount = aliasType[1].trim().split("/03");
        if(qrType[0].trim().equals("STATIC"))
            qrCode = new QrCode(alias[0].trim(),aliasType[0].trim(), qrType[0].trim());
        else if(amount[1].trim().equals("") || amount[1].trim().isEmpty() ||amount[1].trim() == null)
            qrCode = new QrCode(qrType[0].trim(),alias[0].trim(),aliasType[0].trim(),amount[0].trim());
        else
            qrCode = new QrCode(qrType[0].trim(),alias[0].trim(),aliasType[0].trim(),amount[0].trim(),amount[1].trim());

        return qrCode;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getMainDrawerActivity(), Manifest.permission.CAMERA)) {
                Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    public void successRead(QrCode qrCode){
        isQRSucess = true;
        qrCodeReaderView.stopCamera();
        Log.d(qrCode.toString());
        if(qrCode.getQrType().equals("DYNAMIC")) {
            ConfirmQRPaymentFragment fragObj = new ConfirmQRPaymentFragment();
            fragObj.setRequestOBj(qrCode);
            getMainDrawerActivity().addDockableFragment(fragObj);
        }
        else{
            StaticQrFragment fragObj = new StaticQrFragment();
            fragObj.setRequestOBj(qrCode);
            getMainDrawerActivity().addDockableFragment(fragObj);
        }
        //success
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainDrawerActivity().setOnBackPressedListener(this);
        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }
        else if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
            doBack();
            //do back
            /*SelectCardFragment fragObjQR = new SelectCardFragment();
            fragObjQR.setRequestOBj("QR Pay");
            getMainActivity().addDockableFragment(fragObjQR);*/
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void onBackButtonPressed() {
        getMainDrawerActivity().popBack();
    }

    @Override
    public void doBack() {
        onBackButtonPressed();
    }


}
