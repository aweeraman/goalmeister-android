package com.goalmeister;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class AuthenticatorService extends Service {

  private AuthenticatorImpl authenticatorImpl;

  public AuthenticatorService() {
    super();
  }

  @Override
  public void onCreate() {
    authenticatorImpl = getAuthenticator();
  }

  @Override
  public IBinder onBind(Intent intent) {
    IBinder binder = null;
    if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
      binder = getAuthenticator().getIBinder();
    }
    return binder;
  }

  private AuthenticatorImpl getAuthenticator() {
    if (authenticatorImpl == null) {
      authenticatorImpl = new AuthenticatorImpl(this);
    }
    return authenticatorImpl;
  }

  private class AuthenticatorImpl extends AbstractAccountAuthenticator {
    private Context context;

    private AuthenticatorImpl(Context context) {
      super(context);
      this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
        String authTokenType, String[] requiredFeatures, Bundle options)
        throws NetworkErrorException {

      Bundle reply = new Bundle();

      Intent intent = new Intent(context, LoginActivity_.class);
      intent.setAction("com.goalmeister.login");
      intent.putExtra(LoginActivity.ACCOUNT_TYPE, accountType);
      intent.putExtra(LoginActivity.AUTH_TYPE, authTokenType);
      intent.putExtra(LoginActivity.NEW_ACCOUNT, true);
      intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

      reply.putParcelable(AccountManager.KEY_INTENT, intent);
      return reply;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
        Bundle options) throws NetworkErrorException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
        String authTokenType, Bundle options) throws NetworkErrorException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
        String authTokenType, Bundle options) throws NetworkErrorException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
        String[] features) throws NetworkErrorException {
      final Bundle result = new Bundle();
      result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
      return result;
    }
  }

}
