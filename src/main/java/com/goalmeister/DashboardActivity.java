package com.goalmeister;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@EActivity
public class DashboardActivity extends ActionBarActivity {

  @App
  GoalmeisterApp app;

  @StringArrayRes(R.array.drawer_items)
  String[] drawerListViewItems;

  @ViewById(R.id.left_drawer)
  ListView drawerListView;

  @ViewById(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  ActionBar actionBar;
  ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(getBaseContext(), R.layout.drawer_listview_item,
            drawerListViewItems);
    drawerListView.setAdapter(adapter);
    drawerListView.setItemChecked(1, true);
    drawerListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
      }
    });
    drawerToggle =
        new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.open_drawer,
            R.string.close_drawer);
    drawerLayout.setDrawerListener(drawerToggle);

    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayUseLogoEnabled(false);
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setIcon(R.drawable.logo);
  }

  public void selectItem(int position) {
    drawerListView.setItemChecked(position, true);
    drawerLayout.closeDrawer(drawerListView);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);
    drawerToggle.onConfigurationChanged(config);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.dashboard, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    switch (item.getItemId()) {
      case R.id.logout:
        app.setAccessToken(null);
        finish();
    }

    return true;
  }
}
