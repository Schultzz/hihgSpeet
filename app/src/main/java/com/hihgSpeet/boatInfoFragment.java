package com.hihgSpeet;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoatInfoFragment extends ListFragment {

    private double lat, lon;
    private BoatDb db;

    private ArrayList<String> listItems = new ArrayList();

    public BoatInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boat_info, container, false);
    }

    // Called by the localbroadcast service, when there are new data from the GCM service.
    public void updateList(String msgJson) {

        JSONObject msg;

        if (msgJson != null) {
            try {
                msg = new JSONObject(msgJson);

                lat = Double.parseDouble(msg.getString("lat"));
                lon = Double.parseDouble(msg.getString("lon"));

                listItems.set(0, "Speed: " + msg.getString("speed"));
                listItems.set(1, "Heading: " + msg.getString("heading"));
                listItems.set(2, "Latitude: " + lat);
                listItems.set(3, "Longitude: " + lon);
                listItems.set(4, "ErrorMargin: " + msg.getString("err"));
            } catch (JSONException e) {
                System.err.println("jsonParser " + e);
            }
            updateAdapter();
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listItems.add("Speed:");
        listItems.add("Heading:");
        listItems.add("Latitude:");
        listItems.add("Longitude:");
        listItems.add("ErrorMargin:");
        listItems.add("           Save current location");

        updateAdapter();


        //TODO Fjern efter alle har fået noget i deres DB
        db = new BoatDb(this.getContext());
        db.open();

    }

    // TODO: Remove? Only used for testing/debuging purpose.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), this.getListAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
        if (position == 5) {
            // rest.getJSON();
            db.fillDatabase();
            // System.out.println(db.createCoordinates(lat, lon));
        } else if (position == 1) {
            db.deleteTable();
        } else if (position == 0) {
            Cursor c = db.getCoordinates();
            c.moveToFirst();
            do {
                System.err.println(c.getInt(0) + " " + c.getInt(1) + " " + c.getFloat(2) + " " + c.getFloat(3));
            } while (c.moveToNext());
            c.close();

            Cursor c1 = db.getCoordinates1();
            c1.moveToFirst();
            do {
                System.err.println(c1.getInt(0));
            } while (c.moveToNext());
            c.close();
            // Toast.makeText(getActivity(), db.getCoordinates().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //Add new item to ListView
    public void addItem(String item) {

        listItems.add(item);
        updateAdapter();

    }

    //Updates ListView.
    public void updateAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
    }

}
