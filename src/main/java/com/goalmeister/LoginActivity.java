package com.goalmeister;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

  @App
  GoalmeisterApp app;

  @ViewById
  EditText loginUsername;

  @ViewById
  EditText loginPassword;

  ActionBar actionBar;

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
  public void authenticationResult(boolean successful) {
    if (successful) {
      Intent intent = new Intent(this, DashboardActivity_.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
    } else {
      Toast.makeText(this, getResources().getString(R.string.failed_login), Toast.LENGTH_LONG)
          .show();
    }
  }

  @Click
  public void login() {
    authenticateInBackground(loginUsername.getText().toString(), loginPassword.getText().toString());
  }

  @Background
  public void authenticateInBackground(String username, String password) {
    authenticationResult(app.getServiceHelper().authenticate(username, password));
  }
}
