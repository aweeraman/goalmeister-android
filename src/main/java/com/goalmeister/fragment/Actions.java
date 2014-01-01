package com.goalmeister.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goalmeister.R;

public class Actions extends Fragment {
  
  public static Fragment newInstance(Context context) {
    return new Actions();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return (ViewGroup) inflater.inflate(R.layout.fragment_actions, null);
  }
}
