package com.hihgSpeet;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class boatInfoFragment extends ListFragment {

    private double lat, lon;
    private BoatDb db;
    private ArrayList<String> listItems = new ArrayList();

    public boatInfoFragment() {
        // Required empty public constructor
    }

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        db = new BoatDb(this.getContext());
        db.open();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), this.getListAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
        if (position == 5) {
            // rest.getJSON();
            System.out.println("inside 5.....................");
            Intent intent = new Intent(getActivity(), RestService.class);
            intent.putExtra("json", "bjørn");
            getActivity().startService(intent);

           // System.out.println(db.createCordinats(lat, lon));
        } else if (position == 1) {
            db.deleteTable();
        } else if (position == 0) {
            Cursor c = db.getCordinats();
            c.moveToFirst();
            do {
                System.err.println(c.getString(1) + " " + c.getString(2));
            } while (c.moveToNext());
            c.close();
            // Toast.makeText(getActivity(), db.getCordinats().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    public void addItem(String item) {

        listItems.add(item);
        updateAdapter();

    }

    public void updateAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
    }

}
