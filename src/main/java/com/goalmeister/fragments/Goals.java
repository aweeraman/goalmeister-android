package com.goalmeister.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.goalmeister.R;

public class Goals extends Fragment {

  GridView gridView;

  static final String[] goals = new String[] {"Jump out of an airplane", "Write a novel", "Spend a week in Tuscany", "Go on a safari in Kalahari"};

  public static Fragment newInstance(Context context) {
    return new Goals();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = (ViewGroup) inflater.inflate(R.layout.fragment_goals, null);

    gridView = (GridView) v.findViewById(R.id.gridView);
    ArrayAdapter<String> a =
        new ArrayAdapter<String>(getActivity(), R.layout.goals_gridview_item, R.id.gridTextView,
            goals);
    gridView.setAdapter(a);

    return v;
  }
}
