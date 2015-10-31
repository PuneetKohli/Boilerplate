package com.coep.puneet.boilerplate.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.coep.puneet.boilerplate.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivityEvent extends BaseActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    ArrayList<Marker> markersList = new ArrayList<>();
    ArrayList<LatLng> positionList = new ArrayList<>();
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpMapIfNeeded();

    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_maps_activity_event;
    }

    @Override
    protected void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Maps");
    }

    @Override
    protected void setupLayout()
    {

    }

    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(Marker marker)
                    {
                        for (int i = 0; i < markersList.size(); i++)
                        {
                            if (marker.equals(markersList.get(i)))
                            {
                            }
                        }
                        return false;
                    }
                });
                setUpMap();
            }
        }
    }

    private void setUpMap()
    {
        for (int i = 0; i < manager.eventList.size(); i++)
        {
            positionList.add(new LatLng(manager.eventList.get(i).getCoordinates().getLatitude(), manager.eventList.get(i).getCoordinates().getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(positionList.get(i))
                    .title(manager.eventList.get(i).getEventName())
                    .snippet(manager.eventList.get(i).getEventLocation()) //changed to category
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.markers)));
            markersList.add(marker);
        }
        for (int i = 0; i < positionList.size(); i++)
        {
            if (positionList.get(i) == new LatLng(0.0, 0.0))
            {
                markersList.get(i).remove();
            }
        }
        //mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").icon(BitmapDescriptorFactory
        //        .fromResource(R.drawable.markers)));
        /*mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(manager.lat), Double.parseDouble(manager.lng))), 1750,
                null);*/
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                zoomToCoverAllMarkers(positionList, mMap);
            }
        });

    }

    private static void zoomToCoverAllMarkers(LatLng src, LatLng dest, GoogleMap googleMap)
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        builder.include(dest);
        builder.include(src);

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLng(bounds.getCenter());
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);
    }

    private static void zoomToCoverAllMarkers(ArrayList<LatLng> list, GoogleMap googleMap)
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).latitude != 0.0 && list.get(i).longitude != 0.0)
            {
                builder.include(list.get(i));
            }
        }
        LatLngBounds bounds = builder.build();
        int padding = 10;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
