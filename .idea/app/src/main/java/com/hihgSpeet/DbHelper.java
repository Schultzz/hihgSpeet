package com.hihgSpeet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by asd on 14-12-2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "datastorage";
    public static final int VERSION = 1;
    public static final String TABLE = "boatPos";
    public static final String ID_COLUMN = "_id";
    public static final String LAT = "Latitude";
    public static final String LON = "Longitude";

    private static final String LOG_TAG = "BoatDBHelper";

    private static final String CREATE_TABLE_SQL =
            "create table "+TABLE+" ("+
                    ID_COLUMN+" integer primary key autoincrement, "+
                    LAT+" REAL not null, "+
                    LON+" REAL not null );";
    private static final String DROP_TABLE_SQL =
            "drop table if exists "+TABLE+";";


    public DbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_SQL);
            Log.v(LOG_TAG, "Database " + DATABASE + " created");
        }
        catch (SQLiteException sqle) {
            Log.e(LOG_TAG, "Could not create database "+DATABASE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SQL);
        onCreate(db);
    }

}
