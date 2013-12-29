package com.goalmeister;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import android.util.Base64;
import android.util.Log;

import com.goalmeister.model.UserToken;

public class ServiceHelper {

  private GoalmeisterApp app;

  private RestAdapter restAdapter;
  private RestService restService;

  private static final String TAG = "ServiceHelper";

  public ServiceHelper(GoalmeisterApp app) {
    this.app = app;

    restAdapter = new RestAdapter.Builder().setServer(app.getBaseUri()).build();
    restService = restAdapter.create(RestService.class);
  }

  public boolean authenticate(String username, String password) {
    String authHeaderValue = app.getClientId() + ":" + app.getClientSecret();
    String encodedHeader =
        "Basic " + Base64.encodeToString(authHeaderValue.getBytes(), Base64.NO_WRAP).trim();
    Log.i(TAG, ">" + encodedHeader + "<");
    try {
      UserToken userToken =
          restService.authToken(encodedHeader, "password", username, password, app.getClientId(),
              app.getClientSecret());
      if (userToken != null && userToken.access_token != null) {
        return true;
      }
    } catch (RetrofitError error) {
      if (error.getResponse().getStatus() == 401) {
        Log.e(TAG, "Authentication failed");
      } else {
        Log.e(TAG, "Other handled error during authentication: " + error.getResponse().getReason());
      }
    }
    return false;
  }

}
