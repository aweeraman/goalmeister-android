package com.goalmeister;

import retrofit.RestAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.OnClick;

import com.goalmeister.model.UserToken;

public class MainActivity extends Activity {

  public static UserToken userToken;

  private final static String BASE_URI = "http://192.168.1.5:8080";
  private final static String CLIENT_ID = "a26bdb49-a557-451a-9ebf-8965b94d9e66";
  private final static String CLIENT_SECRET = "ede2105b-049d-4d3b-878b-7a3a4ec0427f";

  private static RestAdapter restAdapter = new RestAdapter.Builder().setServer(BASE_URI).build();

  EditText username;
  EditText password;
  TextView token;
  Button login;
  StringBuffer buf;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    username = (EditText) findViewById(R.id.username);
    password = (EditText) findViewById(R.id.password);
    token = (TextView) findViewById(R.id.token);
    login = (Button) findViewById(R.id.login);
    login.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        new RestCall().execute(username.getText().toString(), password.getText().toString());
      }
    });
  }

  private class RestCall extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
      String username = params[0];
      String password = params[1];
      AuthenticationService authService = restAdapter.create(AuthenticationService.class);
      userToken =
          authService
              .authToken(
                  "Basic YTI2YmRiNDktYTU1Ny00NTFhLTllYmYtODk2NWI5NGQ5ZTY2OmVkZTIxMDViLTA0OWQtNGQzYi04NzhiLTdhM2E0ZWMwNDI3Zg==",
                  "password", username, password, CLIENT_ID, CLIENT_SECRET);
      
      token.post(new Runnable() {

        @Override
        public void run() {
          token.setText(userToken.access_token);
        }

      });
      return userToken.access_token;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
}
