package com.factual.ogbroadcastreceiverexample;

import android.content.Context;
import android.util.Log;

import com.factual.FactualConfigMetadata;
import com.factual.FactualError;
import com.factual.FactualInfo;
import com.factual.observationgraph.FactualClientReceiver;

public class LoggingFactualClientReceiver extends FactualClientReceiver {
  @Override
  public void onContext(Context context) {
    // if you need anything from the context
  }

  // Implements the FactualClientListener interface:
  @Override
  public void onStarted() {
    Log.i("og-sdk", "Observation Graph Client started");
  }

  @Override
  public void onStopped() {
    Log.i("og-sdk","Observation Graph Client stopped");
  }

  @Override
  public void onError(FactualError e) {
    Log.e("og-sdk", "Error: " + e.getMessage());
  }

  @Override
  public void onInfo(FactualInfo i) {
    Log.i("og-sdk", "Info: " + i.getInfo());
  }

  @Override
  public void onConfigLoad(FactualConfigMetadata factualConfigMetadata) {
    Log.i("og-sdk", "Config loaded with version: " + factualConfigMetadata.getVersion());
  }
}
