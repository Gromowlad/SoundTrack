package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetCurrentLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        geocoder = new Geocoder(this);

        //build Google API Client
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                List<Address> geoResult = findGeocoder(latitude, longitude);

                //Toast.makeText(getApplicationContext(), "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

                if(geoResult != null) {
                    List<String> geoStringResult = new ArrayList<String>();
                    for (int i = 0; i < geoResult.size(); i++) {
                        Address thisAddress = geoResult.get(i);
                        String stringThisAddress = "";
                        for (int a = 0; a < thisAddress.getMaxAddressLineIndex(); a++) {
                            stringThisAddress += thisAddress.getAddressLine(a) + "\n";
                        }

                        stringThisAddress += thisAddress.getLocality();
                        geoStringResult.add(stringThisAddress);
                    }
                    Toast.makeText(getApplicationContext(), "City: " + geoStringResult.get(0), Toast.LENGTH_LONG).show();

                }

            }
        }
        catch(SecurityException e) {
            Toast.makeText(getApplicationContext(), "Security error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection failed", connectionResult.toString());
    }

    public void updateLocationBtn(View view) {

    }

    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

    }

    private List<Address> findGeocoder(double lat, double lon) {
        final int maxResults = 5;
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, maxResults);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return addresses;
    }

}
