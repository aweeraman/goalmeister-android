package com.goalmeister;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.goalmeister.model.UserToken;

public class ServiceHelper {

  public static UserToken userToken;

  private final static String BASE_URI = "http://192.168.1.5:8080";
  private final static String CLIENT_ID = "a26bdb49-a557-451a-9ebf-8965b94d9e66";
  private final static String CLIENT_SECRET = "ede2105b-049d-4d3b-878b-7a3a4ec0427f";

  private static RestAdapter restAdapter = new RestAdapter.Builder().setServer(BASE_URI).build();

  public static boolean authenticate(String username, String password) {
    AuthenticationService authService = restAdapter.create(AuthenticationService.class);
    try {
      userToken =
          authService
              .authToken(
                  "Basic YTI2YmRiNDktYTU1Ny00NTFhLTllYmYtODk2NWI5NGQ5ZTY2OmVkZTIxMDViLTA0OWQtNGQzYi04NzhiLTdhM2E0ZWMwNDI3Zg==",
                  "password", username, password, CLIENT_ID, CLIENT_SECRET);
    } catch (RetrofitError error) {
      if (error.getResponse().getStatus() == 401) {
        return false;
      }
    }

    if (userToken != null && userToken.access_token != null) {
      return true;
    }
    return false;
  }

}
