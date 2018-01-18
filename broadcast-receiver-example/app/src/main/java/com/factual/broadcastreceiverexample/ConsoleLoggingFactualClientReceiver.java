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
  @Override
  public void onContext(Context context) {
    // If you need fields from the context
  }

  @Override
  public void onStarted() {
    Log.i("engine", "Engine has started.");
    // Example of getting a list of candidates for my current location. (results will show up in
    // logging console, so this example won't be interesting outside of the device emulator).
    FactualEngine.getPlaceCandidates(new ConsoleLoggingFactualPlacesListener());
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
