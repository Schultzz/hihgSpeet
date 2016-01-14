package com.hihgSpeet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.hihgSpeet.DbHelper.ID_COLUMN;
import static com.hihgSpeet.DbHelper.LAT;
import static com.hihgSpeet.DbHelper.LON;
import static com.hihgSpeet.DbHelper.ROUTEDATE;
import static com.hihgSpeet.DbHelper.TABLE_COORDINATES;
import static com.hihgSpeet.DbHelper.TABLE_ROUTES;

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

    public void close() {
        db.close();
    }

    public void open() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException sqle) {
            Log.w(LOG_TAG, "Could not open database for writing " + sqle.getMessage());
            // Log.println(Log.WARN, LOG_TAG, "message... ");
            db = helper.getReadableDatabase();
        }
    }

    //Used to create new Coordinates.
    public long createCoordinates(double lat, double lon) {
        try {
            ContentValues values = new ContentValues();
            values.put(LAT, lat);
            values.put(LON, lon);
            return db.insert(TABLE_COORDINATES, null, values);
        } catch (SQLiteException sqle) {
            Log.w(LOG_TAG, "Could not create coordinates " + sqle.getMessage());
            return -1;
        }
    }

    //TODO: Remove these
    public Cursor getCoordinates() {
        return db.rawQuery("select * from " + TABLE_COORDINATES, null);
    }

    public Cursor getCoordinates1() {
        return db.rawQuery("SELECT " + ID_COLUMN + " FROM " + TABLE_ROUTES + " WHERE " + ROUTEDATE + " = " + "'01-01-2016" + "';", null);
    }

    public void deleteTable() {

        db.execSQL("delete from boatPos");

    }

    public List<String> fetchRouteNames() {

        Cursor cursor = db.rawQuery("SELECT " + ROUTEDATE + " FROM " + TABLE_ROUTES, null);

        cursor.moveToFirst();

        List<String> list = new ArrayList<String>();

        do {

            list.add(cursor.getString(0));

        } while (cursor.moveToNext());

        cursor.close();

        return list;
    }

    public List<LatLng> fectCoordinates(String routeDate) {


        Cursor cursor = db.rawQuery("SELECT " + LAT + ", " + LON + " FROM " + TABLE_COORDINATES + " WHERE " + ROUTEDATE + " = "
                + "(SELECT " + ID_COLUMN + " FROM " + TABLE_ROUTES + " WHERE " + ROUTEDATE + " = '" + routeDate + "');", null);

        cursor.moveToFirst();

        List<LatLng> list = new ArrayList<LatLng>();
        LatLng tempLatLng;

        do {
            tempLatLng = new LatLng(cursor.getDouble(0), cursor.getDouble(1));
            list.add(tempLatLng);
        } while (cursor.moveToNext());

        cursor.close();

        return list;
    }


    // fills the database with test data
    public void fillDatabase() {

        db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES (null, '01-01-2016');");
        Cursor cursor = db.rawQuery("SELECT " + ID_COLUMN + " FROM " + TABLE_ROUTES + " WHERE " + ROUTEDATE + " = '01-01-2016';", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.772780, 12.486029);");
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.774180, 12.487896);");
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.772695, 12.490321);");
        cursor.close();

        db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES (null, '02-01-2016');");
        cursor = db.rawQuery("SELECT " + ID_COLUMN + " FROM " + TABLE_ROUTES + " WHERE " + ROUTEDATE + " = '02-01-2016';", null);
        cursor.moveToFirst();
        id = cursor.getInt(0);
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.767959, 12.469413);");
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.767918, 12.463422);");
        db.execSQL("INSERT INTO " + TABLE_COORDINATES + " VALUES (null, " + id + ", 55.772100, 12.455844);");

        cursor.close();

    }
}