package com.hihgSpeet;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


public class MapDbFragment extends Fragment implements OnMapReadyCallback {

    private BoatDb db;
    private Boolean spinnerFirst = true;
    private static View view;

    private MapView mapView;
    private GoogleMap mMap;

    public MapDbFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new BoatDb(this.getContext());
        db.open();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = ((SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map2));
        mapFragment.getMapAsync(this);
        setupSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map_db, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void setupSpinner() {


        final List<String> items;
        items = db.fetchRouteNames();

        final Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerFirst) {

                    fillMapWithMarkers(items.get(position));
                }

                spinnerFirst = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }


    public void fillMapWithMarkers(String routeDate) {

        mMap.clear();

        List<LatLng> coordinates = db.fectCoordinates(routeDate);
        for(LatLng temp : coordinates){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(temp.latitude, temp.longitude))
                    .title("Route"));
        }

        mMap.addPolyline(
                new PolylineOptions()
                        .addAll(coordinates)
                        .width(5)
                        .color(Color.RED));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(55.758735, 12.482387), 12.0f) );
    }
}
