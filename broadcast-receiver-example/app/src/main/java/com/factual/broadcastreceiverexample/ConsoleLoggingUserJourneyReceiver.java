package com.factual.broadcastreceiverexample;

import android.content.Context;
import android.util.Log;

import com.factual.engine.api.mobile_state.UserJourneyEvent;
import com.factual.engine.api.mobile_state.UserJourneyReceiver;

import org.json.JSONException;

/**
 * Created by arthurwolf on 1/22/18.
 */

public class ConsoleLoggingUserJourneyReceiver extends UserJourneyReceiver {
  @Override
  public void onContext(Context context) {
    // If you need fields from the context
  }

  @Override
  public void onUserJourneyEvent(UserJourneyEvent userJourneyEvent) {
    try {
      Log.i("engine", "Receieved User Journey event: " + userJourneyEvent.toJson().toString());
    } catch (JSONException e) {
      Log.e("engine", "Error with User Journey json");
    }
  }
}
