package com.goalmeister;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

  @ViewById
  TextView helloText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    helloText.setText("Splash");
  }
}
