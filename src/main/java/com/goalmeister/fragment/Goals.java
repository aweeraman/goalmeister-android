package com.goalmeister.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.goalmeister.R;
import com.goalmeister.database.DbHandler;
import com.goalmeister.database.GoalsCursorAdapter;

public class Goals extends Fragment {

  GridView gridView;
  GoalsCursorAdapter cursorAdapter;

  public static Fragment newInstance(Context context) {
    return new Goals();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = (ViewGroup) inflater.inflate(R.layout.fragment_goals, null);

    gridView = (GridView) v.findViewById(R.id.gridView);

    DbHandler dbHandler = new DbHandler(getActivity(), null, null, 1);
    Cursor cursor = dbHandler.goalsList();
    String columns[] = {DbHandler.GOALS_COLUMN_ID, DbHandler.GOALS_TITLE};
    int[] to = {R.id.gridItemId, R.id.gridTextView};
    cursorAdapter =
        new GoalsCursorAdapter(getActivity(), R.layout.goals_gridview_item, cursor, columns, to, 0);
    gridView.setAdapter(cursorAdapter);

    return v;
  }
}
