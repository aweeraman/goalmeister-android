package com.goalmeister;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@EActivity
public class SplashActivity extends Activity {

  @App
  GoalmeisterApp app;

  private static int TIME_OUT = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {

        startActivity(new Intent(SplashActivity.this, DashboardActivity_.class));
        finish();
      }

    }, TIME_OUT);
  }
}
