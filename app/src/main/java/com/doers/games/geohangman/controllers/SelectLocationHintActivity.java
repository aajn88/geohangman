package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.model.Challenge;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * This is the Map Activity, where user selects a location to be sent to his opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class SelectLocationHintActivity extends RoboFragmentActivity {

    /** No Store Location Button **/
    @InjectView(R.id.noStoreLocationBtn)
    private Button mNoStoreLocationBtn;

    /** Store Location Button **/
    @InjectView(R.id.storeLocationBtn)
    private Button mStoreLocationBtn;

    /** GeoHangman Main Service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    /** Google Map **/
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        initOtherViews();
    }

    /**
     * This method updates other views as buttons
     */
    private void initOtherViews() {
        mStoreLocationBtn.setEnabled(Boolean.FALSE);
        mStoreLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(v);
            }
        });
    }

    /**
     * Determines which actions must to be executed when a view launches an event
     * @param v view that launches the event
     */
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.storeLocationBtn:
                sendTypeWordIntent(Boolean.FALSE);
                break;
            case R.id.noStoreLocationBtn:
                sendTypeWordIntent(Boolean.TRUE);
                break;
        }
    }

    private void sendTypeWordIntent(boolean clearLocation) {
        if(clearLocation) {
            geoHangmanService.clearLocation();
        }

        Intent typeWordIntent = new Intent(this, TypeWordActivity.class);
        startActivity(typeWordIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This method sets up the map when is touched to add markers
     */
    private void setUpMap() {

        Challenge.MapPoint mapPoint = geoHangmanService.getStoredLocation();

        if(mapPoint != null) {
            LatLng newLatLng = new LatLng(mapPoint.getLat(), mapPoint.getLng());
            mMap.addMarker(new MarkerOptions().position(newLatLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, mapPoint.getZoom()));
            mStoreLocationBtn.setEnabled(Boolean.TRUE);
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                mStoreLocationBtn.setEnabled(Boolean.TRUE);
                geoHangmanService.storeLocation(latLng.latitude, latLng.longitude, mMap.getCameraPosition().zoom);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mStoreLocationBtn.setEnabled(Boolean.FALSE);
                geoHangmanService.clearLocation();
            }
        });
    }
}
