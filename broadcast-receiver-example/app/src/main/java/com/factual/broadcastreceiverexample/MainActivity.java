package com.factual.broadcastreceiverexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.factual.FactualCircumstance;
import com.factual.FactualException;
import com.factual.engine.FactualEngine;
import com.factual.engine.api.FactualCircumstanceException;

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
    FactualEngine.setReceiver(ConsoleLoggingFactualClientReceiver.class);

    // ConsoleLoggingUserJourneyReceiver extends UserJourneyReceiver, the BroadcastReceiver version of UserJourneyListener
    FactualEngine.setUserJourneyReceiver(ConsoleLoggingUserJourneyReceiver.class);

    // register a handler for the action "log-event", and map it to ConsoleLoggingActionReceiver (see below)
    // ConsoleLoggingActionReceiver extends FactualActionReceiver, the BroadcastReceiver version of FactualActionHandler
    String actionId = "log-event";
    FactualEngine.registerAction(actionId, ConsoleLoggingActionReceiver.class);
    // Example of *optionally* creating circumstances client-side rather than through the Garage UI.
    // The handler associated with the action id "log-event" will be invoked if this circumstance is
    // met.
    this.registerClientSideCircumstance(actionId);
  }

  private void registerClientSideCircumstance(String actionId) {
    // register a circumstance to fire off any time a user stops at or near any Factual place.
    // more info on Factual places: http://developer.factual.com/places/
    // more info on expressions:    http://developer.factual.com/engine/circumstances/
    String circumstanceId = "myCircumstanceId";
    String expression = "(or (at any-factual-place) (near any-factual-place))";

    FactualCircumstance circumstance = new FactualCircumstance(circumstanceId, expression, actionId);
    try {
      FactualEngine.registerCircumstance(circumstance);
    } catch(FactualCircumstanceException e){
      Log.e("engine", e.getMessage());
    }
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