package com.goalmeister;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

  @App
  GoalmeisterApp app;

  @ViewById
  EditText loginUsername;

  @ViewById
  EditText loginPassword;

  @UiThread
  public void authenticationResult(boolean successful) {
    if (successful) {
      startActivity(new Intent(this, DashboardActivity_.class));
    } else {
      Toast.makeText(this, "Login failed, please try again", Toast.LENGTH_LONG).show();
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
