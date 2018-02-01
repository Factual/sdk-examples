package com.factual.broadcastreceiverexample;

import android.content.Context;
import android.util.Log;

import com.factual.FactualCircumstance;
import com.factual.FactualError;
import com.factual.engine.api.CircumstanceResponse;
import com.factual.engine.api.FactualActionReceiver;
import com.factual.engine.api.FactualPlace;

import java.util.List;

public class ConsoleLoggingActionReceiver extends FactualActionReceiver {

  public static void logPlaces(List<FactualPlace> places){
    for (FactualPlace place : places) {
      // name of business
      String name = place.getName();

      // confidence that engine thinks you are there.
      switch (place.getThresholdMet()) {
        case HIGH:
          Log.i("engine", "I'm certainly at " + name);
          break;
        case LOW:
          Log.i("engine", "I'm most likely at " + name);
          break;
        case NONE:
          Log.i("engine", name + " is near me, but I'm not there.");
          break;
      }

      // other stuff you could know:
      double distance = place.getDistance(); // meters
      double lat = place.getLatitude();
      double lng = place.getLongitude();

      // see http://developer.factual.com/places/categories/
      List<Integer> categoryIds = place.getCategoryIds();
    }
  }

  @Override
  public void onContext(Context context) {
    // If you need fields from the context
  }

  @Override
  public void onCircumstancesMet(List response) {
    // if multiple circumstances are met simultaneously, they will all be in this list together.
    for (CircumstanceResponse circumstanceMet : (List<CircumstanceResponse>) response) {
      Log.i("Engine Action Handler", "met " + circumstanceMet.getCircumstance().getCircumstanceId());

      // contains 'at' places that meet the circumstance criteria
      logPlaces(circumstanceMet.getAtPlaces());

      //  contains 'near' places that meet the circumstance criteria
      // e.g., if circumstances required being near an Arby's, the specific Arby's will be here.
      logPlaces(circumstanceMet.getNearPlaces());
    }
  }

  @Override
  public void onCircumstanceError(FactualCircumstance circumstance, FactualError error){
    Log.e("Engine Action Handler", error.getMessage() + " in " + circumstance.getCircumstanceId());
  }
}
