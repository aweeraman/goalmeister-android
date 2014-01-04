package com.goalmeister.api;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import android.util.Base64;
import android.util.Log;

import com.goalmeister.AppConfig;
import com.goalmeister.GoalmeisterApp;
import com.goalmeister.Response;
import com.goalmeister.model.UserToken;

public class ServiceHelper {

  private GoalmeisterApp app;

  private RestAdapter restAdapter;
  private ApiDelegate api;

  private static final String TAG = "ServiceHelper";

  public ServiceHelper(GoalmeisterApp app) {
    this.app = app;

    restAdapter = new RestAdapter.Builder().setServer(AppConfig.BASE_URI).build();
    api = restAdapter.create(ApiDelegate.class);
  }

  public Response authenticate(String username, String password) {
    String authHeaderValue = AppConfig.CLIENT_ID + ":" + AppConfig.CLIENT_SECRET;
    String encodedHeader = Base64.encodeToString(authHeaderValue.getBytes(), Base64.NO_WRAP).trim();
    try {
      UserToken userToken =
          api.authToken("Basic " + encodedHeader, "password", username, password,
              AppConfig.CLIENT_ID, AppConfig.CLIENT_SECRET);
      if (userToken != null && userToken.access_token != null) {
        app.setAccessToken(userToken.access_token);
        return Response.STATUS_OK;
      }
    } catch (RetrofitError error) {
      if (error.getResponse() == null) {
        Log.e(TAG, "Server is down");
        return Response.ERROR_SERVER_DOWN;
      } else if (error.getResponse().getStatus() == 401) {
        Log.e(TAG, "Authentication failed for " + username + "/" + password);
        return Response.ERROR_INVALID_USERNAME_OR_PASSWORD;
      } else {
        Log.e(TAG, "Unknown error during authentication: " + error.getResponse().getReason());
        return Response.STATUS_ERROR;
      }
    }
    return Response.STATUS_OK;
  }

}
