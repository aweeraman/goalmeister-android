package com.goalmeister.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class GoalsProvider extends ContentProvider {

  private DbHandler dbHandler;

  public static final String AUTHORITY = "com.goalmeister.database.provider";
  public static final String GOALS_TABLE = "goals";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GOALS_TABLE);

  public static final int GOALS = 1;
  public static final int GOALS_ID = 2;

  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    sURIMatcher.addURI(AUTHORITY, GOALS_TABLE, GOALS);
    sURIMatcher.addURI(AUTHORITY, GOALS_TABLE + "/#", GOALS_ID);
  }

  @Override
  public boolean onCreate() {
    dbHandler = new DbHandler(getContext(), null, null, 1);
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {

    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    int uriType = sURIMatcher.match(uri);
    switch (uriType) {
      case GOALS_ID:
        queryBuilder.appendWhere(DbHandler.GOALS_COLUMN_ID + "=" + uri.getLastPathSegment());
        // fall-through
      case GOALS:
        queryBuilder.setTables(DbHandler.GOALS_TABLE);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    Cursor cursor =
        queryBuilder.query(dbHandler.getReadableDatabase(), projection, selection, selectionArgs,
            null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Override
  public String getType(Uri uri) {
    int uriType = sURIMatcher.match(uri);
    switch (uriType) {
      case GOALS_ID:
        return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + DbHandler.GOALS_CONTENT_TYPE;
      case GOALS:
        return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + DbHandler.GOALS_CONTENT_TYPE;
      default:
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDb = dbHandler.getWritableDatabase();
    long id;
    switch (uriType) {
      case GOALS:
        id = sqlDb.insert(DbHandler.GOALS_TABLE, null, values);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.parse(GOALS_TABLE + "/" + id);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDb = dbHandler.getWritableDatabase();

    int rowsDeleted = 0;

    switch (uriType) {
      case GOALS:
        rowsDeleted = sqlDb.delete(DbHandler.GOALS_TABLE, selection, selectionArgs);
        break;
      case GOALS_ID:
        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
          rowsDeleted =
              sqlDb.delete(DbHandler.GOALS_TABLE, DbHandler.GOALS_COLUMN_ID + "=" + id, null);
        } else {
          rowsDeleted =
              sqlDb.delete(DbHandler.GOALS_TABLE, DbHandler.GOALS_COLUMN_ID + "=" + id + " and "
                  + selection, selectionArgs);
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsDeleted;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDb = dbHandler.getWritableDatabase();

    int rowsUpdated = 0;

    switch (uriType) {
      case GOALS:
        rowsUpdated = sqlDb.update(DbHandler.GOALS_TABLE, values, selection, selectionArgs);
        break;
      case GOALS_ID:
        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
          rowsUpdated =
              sqlDb.update(DbHandler.GOALS_TABLE, values, DbHandler.GOALS_COLUMN_ID + "=" + id,
                  null);
        } else {
          rowsUpdated =
              sqlDb.update(DbHandler.GOALS_TABLE, values, DbHandler.GOALS_COLUMN_ID + "=" + id
                  + " and " + selection, selectionArgs);
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsUpdated;
  }
}
