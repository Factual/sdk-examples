package com.factual.ogbroadcastreceiverexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.factual.FactualException;
import com.factual.observationgraph.FactualObservationGraph;

public class MainActivity extends AppCompatActivity {

  private int LOCATION_FINE_REQUEST = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    try {

      initializeObservationGraphClient();

      /*
       *   We leave checking/obtaining location permissions to you, so that you can lump in
       *   any other permissions you may require, as well as choose the method and timing of the
       *   prompt.
       *
       *   Therefore, the following is for illustrative purposes:
       */

      if (isRequiredPermissionAvailable()) {
        startObservationGraphClient();
      } else {
        requestLocationPermissions();
      }

    } catch (FactualException e) {
      Log.e("og-sdk", e.getMessage());
    }
  }

  // this is how you initialize the client.
  public void initializeObservationGraphClient() throws FactualException {
    FactualObservationGraph.initialize(this, "your api-key goes here"); //TODO: insert your api-key
    FactualObservationGraph.setListener(LoggingFactualClientReceiver.class);
  }

  // and this is how you start it.
  private void startObservationGraphClient() throws FactualException {
    FactualObservationGraph.start();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    if (isRequiredPermissionAvailable()) {
      try {
        startObservationGraphClient();
      } catch (FactualException e) {
        Log.e("og-sdk", e.getMessage());
      }
    } else {
      Log.e("og-sdk", "Necessary permissions were never provided.");
    }
  }

  // These are the permissions that the Observation Graph client require:
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
        LOCATION_FINE_REQUEST);
  }
}
