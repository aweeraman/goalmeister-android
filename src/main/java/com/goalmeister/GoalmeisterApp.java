package com.goalmeister;

import org.androidannotations.annotations.EApplication;

import android.app.Application;

@EApplication
public class GoalmeisterApp extends Application {
  
  private final static String BASE_URI = "http://192.168.1.5:8080";
  private final static String CLIENT_ID = "a26bdb49-a557-451a-9ebf-8965b94d9e66";
  private final static String CLIENT_SECRET = "ede2105b-049d-4d3b-878b-7a3a4ec0427f";
  
  private String accessToken;
  private ServiceHelper serviceHelper;
  
  public GoalmeisterApp() {
    super();
    serviceHelper = new ServiceHelper(this);
  }
  
  public ServiceHelper getServiceHelper() {
    return serviceHelper;
  }
  
  public void setAccessToken(String token) {
    accessToken = token;
  }
  
  public String getAccessToken() {
    return accessToken;
  }

  public String getBaseUri() {
    return BASE_URI;
  }
  
  public String getClientId() {
    return CLIENT_ID;
  }
  
  public String getClientSecret() {
    return CLIENT_SECRET;
  }

}
