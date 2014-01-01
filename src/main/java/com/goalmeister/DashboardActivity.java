package com.goalmeister;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@EActivity
public class DashboardActivity extends ActionBarActivity {

  private static final String TAG = "DashboardActivity";

  @App
  GoalmeisterApp app;

  @StringArrayRes(R.array.drawer_items)
  String[] drawerListViewItems;

  @StringArrayRes(R.array.drawer_fragments)
  String[] drawerFragments;

  @ViewById(R.id.left_drawer)
  ListView drawerListView;

  @ViewById(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  ActionBar actionBar;
  ActionBarDrawerToggle drawerToggle;

  @SystemService
  AccountManager accountManager;

  private Account account;
  private String authToken;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_dashboard);

    loginChooser();

    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(getBaseContext(), R.layout.drawer_listview_item,
            drawerListViewItems);
    drawerListView.setAdapter(adapter);
    selectItem(0);
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

  private void fetchAuthToken() {
    Bundle options = new Bundle();
    accountManager.getAuthToken(account, AppConfig.AUTH_TYPE, options, this, new OnFetchAccount(),
        new Handler(new OnFetchError()));
  }

  private class OnFetchError implements Callback {
    @Override
    public boolean handleMessage(Message msg) {
      Log.e(TAG, "Error fetching auth token");
      return false;
    }
  }

  private class OnFetchAccount implements AccountManagerCallback<Bundle> {
    @Override
    public void run(AccountManagerFuture<Bundle> result) {
      Bundle bundle;
      try {
        bundle = result.getResult();
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
        return;
      }
      authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
      app.setAccessToken(authToken);
      Log.d(TAG, "Received authentication token " + authToken);
    }
  }

  private class OnNewAccountAdd implements AccountManagerCallback<Bundle> {
    @Override
    public void run(AccountManagerFuture<Bundle> result) {
      Bundle bundle;
      try {
        bundle = result.getResult();
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
        return;
      }
      account =
          new Account(bundle.getString(AccountManager.KEY_ACCOUNT_NAME),
              bundle.getString(AccountManager.KEY_ACCOUNT_TYPE));
      Log.d(TAG, "Added account " + account.name);
      fetchAuthToken();
    }
  }

  private void loginChooser() {
    accountManager = AccountManager.get(this);
    Account[] acc = accountManager.getAccountsByType(AppConfig.AUTH_TYPE);

    if (acc.length == 0) {
      // No accounts found, create new account
      accountManager.addAccount(AppConfig.AUTH_TYPE, AppConfig.AUTH_TOKEN_TYPE, null, new Bundle(),
          this, new OnNewAccountAdd(), null);
    } else if (acc.length == 1) {
      // One account found, use that
      account = acc[0];
      fetchAuthToken();
    } else if (acc.length > 1) {
      // More than one account found, select the first account
      Log.d(TAG, "More than one account encountered, selecting the first one");
      account = acc[0];
      fetchAuthToken();
    }
  }

  public void selectItem(int position) {
    drawerListView.setItemChecked(position, true);
    drawerLayout.closeDrawer(drawerListView);

    FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
    tx.replace(R.id.content_frame, Fragment.instantiate(this, drawerFragments[position]));
    tx.commit();
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

    return true;
  }
}
