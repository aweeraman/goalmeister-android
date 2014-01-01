package com.goalmeister;

import org.androidannotations.annotations.EApplication;

import android.accounts.Account;
import android.app.Application;
import android.content.SharedPreferences;

import com.goalmeister.api.ServiceHelper;

@EApplication
public class GoalmeisterApp extends Application {
  
  private final static String ACCESS_TOKEN_PREF_KEY = "access_token";

  private String accessToken;
  
  private ServiceHelper serviceHelper;
  private Account account;
  
  public GoalmeisterApp() {
    super();
    serviceHelper = new ServiceHelper(this);
  }
  
  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public ServiceHelper getServiceHelper() {
    return serviceHelper;
  }
  
  public SharedPreferences getPreferences() {
    return getSharedPreferences("prefs", MODE_PRIVATE);
  }
  
  public void setAccessToken(String token) {
    SharedPreferences.Editor edit = getPreferences().edit();
    edit.putString(ACCESS_TOKEN_PREF_KEY, token);
    edit.commit();
    accessToken = token;
  }
  
  public String getAccessToken() {
    if (accessToken == null) {
      accessToken = getPreferences().getString(ACCESS_TOKEN_PREF_KEY, null);
    }
    return accessToken;
  }
}
