package com.factual.broadcastreceiverexample;

import android.content.Context;
import android.util.Log;

import com.factual.FactualCircumstance;
import com.factual.FactualConfigMetadata;
import com.factual.FactualError;
import com.factual.FactualException;
import com.factual.FactualInfo;
import com.factual.engine.FactualClientReceiver;
import com.factual.engine.FactualEngine;
import com.factual.engine.api.FactualCircumstanceException;

public class ConsoleLoggingFactualClientReceiver extends FactualClientReceiver {
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

  @Override
  public void onContext(Context context) {
    // If you need fields from the context
  }

  @Override
  public void onStarted() {
    Log.i("engine", "Engine has started.");
    // register a handler for the action "log-event", and map it to ConsoleLoggingActionReceiver (see below)
    // ConsoleLoggingActionReceiver extends FactualActionReceiver, the BroadcastReceiver version of FactualActionHandler
    String actionId = "log-event";
    FactualEngine.registerAction(actionId, ConsoleLoggingActionReceiver.class);
    // Example of getting a list of candidates for my current location. (results will show up in
    // logging console, so this example won't be interesting outside of the device emulator).
    try {
      FactualEngine.getPlaceCandidates(new ConsoleLoggingFactualPlacesListener());
    } catch (FactualException e) {
      Log.e("engine", e.getMessage());
    }
    // Example of *optionally* creating circumstances client-side rather than through the Garage UI.
    // The handler associated with the action id "log-event" will be invoked if this circumstance is
    // met.
    this.registerClientSideCircumstance(actionId);
  }

  @Override
  public void onStopped() {
    Log.i("engine", "Engine has stopped.");
  }

  @Override
  public void onError(FactualError e) {
    Log.i("engine", e.getMessage());
  }

  @Override
  public void onInfo(FactualInfo i) {
    Log.i("engine", i.getInfo());
  }

  @Override
  public void onSyncWithGarageComplete() {
    Log.i("engine", "Garage synced.");
  }

  @Override
  public void onConfigLoad(FactualConfigMetadata data) {
    Log.i("engine", "Garage config loaded at: " + data.getVersion());
    if (data.getGarageRelease() != null) {
      for (FactualCircumstance circumstance : data.getGarageRelease().getCircumstances()) {
        Log.i("engine", "loaded circumstance with id: " + circumstance.getCircumstanceId());
      }
    } else {
      Log.i("engine", "Garage release is empty");
    }
  }
}
