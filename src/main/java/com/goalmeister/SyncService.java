package com.goalmeister;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {

  private static String TAG = "SyncService";

  private SyncAdapterImpl syncAdapter;

  public SyncService() {
    super();
  }

  @Override
  public IBinder onBind(Intent intent) {
    if (syncAdapter == null) {
      syncAdapter = new SyncAdapterImpl(this);
    }
    return syncAdapter.getSyncAdapterBinder();
  }

  private class SyncAdapterImpl extends AbstractThreadedSyncAdapter {

    private Context context;

    public SyncAdapterImpl(Context context) {
      super(context, true);
      this.context = context;
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
        ContentProviderClient provider, SyncResult syncResult) {
      Log.i(TAG, "Sync performed");
    }
  }
}
