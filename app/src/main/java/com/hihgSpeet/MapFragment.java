package com.hihgSpeet;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Søren on 14-12-2015.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private Marker currentMarker;
    private ArrayList<Marker> markers = new ArrayList();
    private Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMapFragment();

        //Setup our listener for the navigate button
        Button btn = (Button) getActivity().findViewById(R.id.Navigatebutton);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(markers.size()>0){
                    ArrayList<LatLng> latLngList = new ArrayList<LatLng>();
                    for(Marker marker : markers){
                        latLngList.add(marker.getPosition());
                    }
                    String jsonString = gson.toJson(latLngList);
                    Log.d("PER - Navigate", jsonString);
                    Toast.makeText(getActivity(), "Get ready... This is gonna get wild",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Select waypoints before you can sail...",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void navigateButton() {



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("Debug - Activity", getContext().toString());
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomBy(12));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //LatLong textview on mapfragment
//                TextView latLngTW = (TextView) getActivity().findViewById(R.id.LatLngText);
//                String twString = "Latitude: " + latLng.latitude + ", Longtitude: " + latLng.longitude;
//                latLngTW.setText(twString);
//                Log.d("Debug", twString);
                LatLng wayPoint = new LatLng(latLng.latitude, latLng.longitude);
                Marker newMarker = mMap.addMarker(new MarkerOptions().position(wayPoint).title("Waypoint " + (markers.size() + 1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                markers.add(newMarker);
                if (markers.size() > 1) {
                    Log.d("Debug", "Inside if");
                    mMap.addPolyline(
                            new PolylineOptions()
                                    .add(newMarker.getPosition(), markers.get(markers.size() - 2).getPosition())
                                    .width(5)
                                    .color(Color.RED));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(wayPoint));

            }
        });

    }

    public void setupMapFragment(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ((SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        System.out.println("PER3 " +mapFragment);

        //getFragmentManager(), getActivity().getSupportFragmentManager(), and getChildFragmentManager()

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provide
                if(mMap!=null){


                    if(currentMarker!=null){
                        currentMarker.remove();
                    }

                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    currentMarker =new MarkerOptions().position(currentLatLng).title("You are here")
                    currentMarker = mMap.addMarker(new MarkerOptions().position(currentLatLng).title("You are here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));

                }
                Log.d("Per", location.toString());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };


        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        // Register the listener with the Location Manager to receive location updates
         //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }



}
