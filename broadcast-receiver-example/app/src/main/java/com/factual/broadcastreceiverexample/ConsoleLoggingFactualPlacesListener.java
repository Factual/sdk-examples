package com.factual.broadcastreceiverexample;

import android.util.Log;

import com.factual.FactualError;
import com.factual.engine.api.FactualPlace;
import com.factual.engine.api.FactualPlacesListener;
import com.factual.engine.api.PlaceCandidateResponse;

import java.util.List;

public class ConsoleLoggingFactualPlacesListener implements FactualPlacesListener {

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
  public void onPlacesResponse(PlaceCandidateResponse placeCandidateResponse) {
    Log.i("Engine Places Handler", "place name : likelihood of being there");
    logPlaces(placeCandidateResponse.getCandidates());
  }

  @Override
  public void onPlacesError(FactualError factualError) {
    Log.e("Engine Places Handler", factualError.getMessage() + " code: " + factualError.getCode());
  }
}
