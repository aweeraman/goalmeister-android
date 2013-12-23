package com.goalmeister;

import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.goalmeister.model.Goal;
import com.goalmeister.model.UserToken;

public class MainActivity extends Activity {

  TextView text;
  StringBuffer buf;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    text = (TextView) findViewById(R.id.helloText);
    text.setText("Loading...");

    new RestCall().execute();
  }

  private class RestCall extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... arg0) {
      RestAdapter restAdapter =
          new RestAdapter.Builder().setServer("http://192.168.1.5:8080").build();
      AuthenticationService authService = restAdapter.create(AuthenticationService.class);
      UserToken userToken =
          authService
              .authToken(
                  "Basic YTI2YmRiNDktYTU1Ny00NTFhLTllYmYtODk2NWI5NGQ5ZTY2OmVkZTIxMDViLTA0OWQtNGQzYi04NzhiLTdhM2E0ZWMwNDI3Zg==",
                  "password", "aweeraman@gmail.com", "weeraman",
                  "a26bdb49-a557-451a-9ebf-8965b94d9e66", "ede2105b-049d-4d3b-878b-7a3a4ec0427f");

      GoalService goalService = restAdapter.create(GoalService.class);
      List<Goal> goals = goalService.list("Bearer " + userToken.access_token);

      buf = new StringBuffer();
      for (Goal goal : goals) {
        if ("".equals(buf.toString())) {
          buf.append(goal.title);
        } else {
          buf.append(", ").append(goal.title);
        }
      }
      
      text.post(new Runnable() {
        @Override
        public void run() {
          text.setText(buf.toString());
        }
      });

      return buf.toString();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
}
