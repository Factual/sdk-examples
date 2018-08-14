package com.factual.ogbroadcastreceiverexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.factual.android.FactualException;
import com.factual.android.ObservationGraph;

public class MainActivity extends AppCompatActivity {
  public static final String LOGTAG = MainActivity.class.getName();
  public static final int PERMISSIONS_REQUEST_CODE = 100;
  public static final String API_KEY = "your-api-key";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startOG();
  }

  public void startOG() {
    if(isRequiredPermissionAvailable()) {
      try {
        ObservationGraph.getInstance(this, API_KEY);
      } catch (FactualException e) {
        Log.e(LOGTAG, "Factual Exception: " + e);
      }
    } else {
      requestLocationPermissions();
    }
  }

  public boolean isRequiredPermissionAvailable() {
    return ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.d(LOGTAG,"permissions granted");
        startOG();
      } else {
        Log.e(LOGTAG,"permission denied");
      }
    }
  }

  public void requestLocationPermissions() {
    ActivityCompat.requestPermissions(
        this,
        new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        },
        PERMISSIONS_REQUEST_CODE);
  }
}
