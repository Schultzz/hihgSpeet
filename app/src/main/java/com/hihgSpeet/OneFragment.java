package com.hihgSpeet;

import android.database.Cursor;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OneFragment extends ListFragment {

    private RestService rest;
    private BoatDb db;
    private ArrayList<String> listItems = new ArrayList();

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listItems.add("Speed:");
        listItems.add("Direction:");
        listItems.add("Bearing:");
        listItems.add("Latitude:");
        listItems.add("Longitude:");
        listItems.add("Perform REST:");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        rest = new RestService(this);

        db = new BoatDb(this.getContext());
        db.open();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), this.getListAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
        if (position == 5) {
            rest.getJSON();
        } else if (position == 4) {
            Toast.makeText(getActivity(),  db.createCordinats(11, 20)+"",Toast.LENGTH_SHORT).show();

        } else if (position == 3) {
            Cursor c = db.getCordinats();
            c.moveToFirst();
            do {
                System.out.println(c.getString(2));
            }while(c.moveToNext());

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

    }

}
