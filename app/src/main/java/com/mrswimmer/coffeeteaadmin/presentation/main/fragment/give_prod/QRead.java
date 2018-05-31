package com.mrswimmer.coffeeteaadmin.presentation.main.fragment.give_prod;

/**
 * Created by denis on 31.05.2018.
 */

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.mrswimmer.coffeeteaadmin.App;
import com.mrswimmer.coffeeteaadmin.R;
import com.mrswimmer.coffeeteaadmin.di.qualifier.Global;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;


public class QRead extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {


        finish();
        //resultTextView.setText(text);
    }
    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
}