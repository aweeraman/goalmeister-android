package com.goalmeister.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "goalmeister.db";

  public static final String GOALS_TABLE = "goals";
  public static final String GOALS_COLUMN_ID = "_id";
  public static final String GOALS_TITLE = "title";
  public static final String GOALS_DESCRIPTION = "description";
  public static final String GOALS_CONTENT_TYPE = "com.goalmeister.goals";

  public DbHandler(Context context, String name, CursorFactory factory, int version) {
    super(context, DATABASE_NAME, factory, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_PRODUCTS_TABLE =
        "CREATE TABLE " + GOALS_TABLE + "(" + GOALS_COLUMN_ID + " TEXT PRIMARY KEY, " + GOALS_TITLE
            + " TEXT, " + GOALS_DESCRIPTION + " TEXT)";
    db.execSQL(CREATE_PRODUCTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE);
    onCreate(db);
  }

}
