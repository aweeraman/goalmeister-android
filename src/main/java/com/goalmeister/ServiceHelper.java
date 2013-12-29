package com.goalmeister;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.goalmeister.model.UserToken;

public class ServiceHelper {

  private GoalmeisterApp app;

  private RestAdapter restAdapter;
  private RestService restService;

  public ServiceHelper(GoalmeisterApp app) {
    this.app = app;
    
    restAdapter = new RestAdapter.Builder().setServer(app.getBaseUri()).build();
    restService = restAdapter.create(RestService.class);
  }

  public boolean authenticate(String username, String password) {
    try {
      UserToken userToken =
          restService
              .authToken(
                  "Basic YTI2YmRiNDktYTU1Ny00NTFhLTllYmYtODk2NWI5NGQ5ZTY2OmVkZTIxMDViLTA0OWQtNGQzYi04NzhiLTdhM2E0ZWMwNDI3Zg==",
                  "password", username, password, app.getClientId(), app.getClientSecret());
      if (userToken != null && userToken.access_token != null) {
        return true;
      }
    } catch (RetrofitError error) {
      if (error.getResponse().getStatus() == 401) {
        return false;
      }
    }
    return false;
  }

}
