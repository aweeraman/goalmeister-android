package com.goalmeister.activity;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.goalmeister.AppConfig;
import com.goalmeister.GoalmeisterApp;
import com.goalmeister.R;
import com.goalmeister.Response;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

  @App
  GoalmeisterApp app;

  @ViewById
  EditText loginUsername;

  @ViewById
  EditText loginPassword;

  ActionBar actionBar;

  @SystemService
  AccountManager accountManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
    actionBar.setDisplayUseLogoEnabled(false);
    actionBar.setIcon(android.R.drawable.ic_secure);
  }

  @UiThread
  public void handleAuthResponse(Response resp, String username, String password) {

    if (Response.ERROR_SERVER_DOWN.equals(resp)) {
      // Server is down
      Toast.makeText(this, getResources().getString(R.string.server_down), Toast.LENGTH_LONG)
          .show();
      return;
    } else if (Response.ERROR_INVALID_USERNAME_OR_PASSWORD.equals(resp)) {
      // Invalid username / password
      Toast.makeText(this, getResources().getString(R.string.failed_login), Toast.LENGTH_LONG)
          .show();
      return;
    } else if (Response.STATUS_ERROR.equals(resp)) {
      // Call support
      Toast.makeText(this, getResources().getString(R.string.server_error), Toast.LENGTH_LONG)
          .show();
      return;
    }

    // Everything's peachy by this point
    Account account = new Account(username, AppConfig.AUTH_TYPE);
    boolean created = accountManager.addAccountExplicitly(account, null, null);
    accountManager.setAuthToken(account, AppConfig.AUTH_TYPE, app.getAccessToken());
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      if (created) {
        AccountAuthenticatorResponse response =
            extras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, username);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, AppConfig.AUTH_TYPE);
        response.onResult(result);
      }
    }
    finish();
  }

  @Click
  public void login() {
    String username = loginUsername.getText().toString();
    String password = loginPassword.getText().toString();
    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
      Toast.makeText(this, getResources().getString(R.string.empty_email_password),
          Toast.LENGTH_LONG).show();
      return;
    }
    authenticateInBackground(username, password);
  }

  @Background
  public void authenticateInBackground(String username, String password) {
    handleAuthResponse(app.getServiceHelper().authenticate(username, password), username, password);
  }
}
