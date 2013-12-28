package com.goalmeister;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

  @ViewById
  public EditText loginUsername;

  @ViewById
  public EditText loginPassword;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }

  @UiThread
  public void authenticationResult(boolean successful) {
    if (successful) {
      Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }
  }

  @Click
  public void login() {
    authenticateInBackground(loginUsername.getText().toString(), loginPassword.getText().toString());
  }

  @Background
  public void authenticateInBackground(String username, String password) {
    authenticationResult(ServiceHelper.authenticate(username, password));
  }
}
