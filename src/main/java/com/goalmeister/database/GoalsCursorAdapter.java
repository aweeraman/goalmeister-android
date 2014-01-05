package com.goalmeister.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goalmeister.R;

public class GoalsCursorAdapter extends SimpleCursorAdapter {

  private Context context;
  private LayoutInflater inflater;
  private int newCount;

  public GoalsCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to,
      int flags) {
    super(context, layout, c, from, to, flags);
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    int newCount = super.getCount() + 1;
    return newCount;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (position == (getCount() - 1)) {
      View lastRow = inflater.inflate(R.layout.goals_gridview_add_item, null);
      lastRow.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          Toast.makeText(context, "Add Goal", Toast.LENGTH_LONG).show();
        }
      });
      return lastRow;
    }

    return super.getView(position, convertView, parent);
  }



}
