package com.hihgSpeet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import static com.hihgSpeet.DbHelper.*;

/**
 * Created by asd on 14-12-2015.
 */
public class BoatDb implements AutoCloseable {
    private static final String LOG_TAG = "BoatDB";
    private SQLiteDatabase db;
    private final Context context;
    private final DbHelper helper;

    public BoatDb(Context context) {
        this.context = context;
        helper = new DbHelper(context);
    }

    public void close() { db.close(); }

    public void open() {
        try {
            db = helper.getWritableDatabase();
        }
        catch (SQLiteException sqle) {
            Log.w(LOG_TAG, "Could not open database for writing " + sqle.getMessage());
            // Log.println(Log.WARN, LOG_TAG, "message... ");
            db = helper.getReadableDatabase();
        }
    }

    public long createCordinats(double lat, double lon) {
        try {
            ContentValues values = new ContentValues();
            values.put(LAT, lat);
            values.put(LON, lon);
            return db.insert(TABLE, null, values);
        }
        catch (SQLiteException sqle) {
            Log.w(LOG_TAG, "Could not create high score "+sqle.getMessage());
            return -1;
        }
    }

    public Cursor getCordinats() {
        return db.rawQuery("select * from "+TABLE, null);
    }

    public void deleteTable(){

        db.execSQL("delete from boatPos");

    }
}