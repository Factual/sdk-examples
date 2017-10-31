package com.factual.broadcastreceiverexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.factual.FactualException;
import com.factual.engine.FactualEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initializeEngine();
      /*
       *   We leave checking/obtaining location permissions to you, so that you can lump in
       *   any other permissions you may require, as well as choose the method and timing of the
       *   prompt.
       *
       *   Therefore, the following is for illustrative purposes:
       */
            if (isRequiredPermissionAvailable()) {
                startEngine();
            } else {
                requestLocationPermissions();
            }
        } catch (FactualException e) {
            Log.e("engine", e.getMessage());
        }
    }

    public void initializeEngine() throws FactualException {
        FactualEngine.initialize(this, "your api-key goes here"); // TODO: Put your API key here
        // ConsoleLoggingFactualClientReceiver extends FactualClientReceiver, the BroadcastReceiver version of FactualClientListener
        FactualEngine.setListener(ConsoleLoggingFactualClientReceiver.class);
    }

    private void startEngine() {
        try {
            FactualEngine.start();
        } catch (FactualException e) {
            Log.e("engine", e.getMessage());
        }
    }

    // example permissions boilerplate
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (isRequiredPermissionAvailable()) {
            startEngine();
        } else {
            Log.e("engine", "Necessary permissions were never provided.");
        }
    }

    public boolean isRequiredPermissionAvailable(){
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
            },
            0);
    }
}