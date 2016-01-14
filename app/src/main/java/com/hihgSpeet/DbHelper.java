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
    public static final int VERSION = 8;
    public static final String TABLE_COORDINATES = "boatPos";
    public static final String TABLE_ROUTES = "boatRoutes";
    public static final String ID_COLUMN = "_id";
    public static final String LAT = "Latitude";
    public static final String LON = "Longitude";
    public static final String ROUTEDATE = "Route_Date";

    private static final String LOG_TAG = "BoatDBHelper";

    //String for first table
    private static final String CREATE_TABLE_SQL_ROUTES =
            "create table "+ TABLE_ROUTES +" ("+
                    ID_COLUMN+" integer primary key autoincrement, "+
                    ROUTEDATE+" STRING not null);";


    private static final String CREATE_TABLE_SQL_COORDINATES =
            "create table "+ TABLE_COORDINATES +" ("+
                    ID_COLUMN+" integer primary key autoincrement, "+
                    ROUTEDATE+" integer not null,"+
                    LAT+" REAL not null, "+
                    LON+" REAL not null,"+
                    "FOREIGN KEY ("+ROUTEDATE+") REFERENCES " + TABLE_ROUTES +"(" + ID_COLUMN + ")"+
                    ");";


    private static final String DROP_TABLE_SQL =
            "drop table if exists "+ TABLE_COORDINATES +";";

    private static final String DROP_TABLE_SQL_2 =
            "drop table if exists "+ TABLE_ROUTES +";";


    public DbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_SQL_ROUTES);
            db.execSQL(CREATE_TABLE_SQL_COORDINATES);
            Log.v(LOG_TAG, "Database " + DATABASE + " created");
        }
        catch (SQLiteException sqle) {
            Log.e(LOG_TAG, "Could not create database "+DATABASE + ", error: " + sqle);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SQL);
        db.execSQL(DROP_TABLE_SQL_2);
        onCreate(db);
    }

}
