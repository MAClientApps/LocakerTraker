package com.projector.callertracker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projector.callertracker.R;
import com.projector.callertracker.location.LocationDetected;
import com.projector.callertracker.location.LocationDetector;

public class Map2Activity extends FragmentActivity implements LocationListener,
        OnMapReadyCallback, LocationDetected, GoogleMap.OnMarkerClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    public static int REQUEST_OPEN_LOCATION_SETTING = 151;
    public SupportMapFragment supportMapFragment;
    boolean mTimerIsRunning = false;
    private GoogleMap mMap;
    private ProgressBar progress_bar;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        progress_bar = findViewById(R.id.progress_bar);
        MapsInitializer.initialize(Map2Activity.this);
        progress_bar.setVisibility(View.GONE);
        checkGpsPermission();

    }

    private void requestLocationPermission() {

       /* ActivityCompat.requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_OPEN_LOCATION_SETTING);*/
    }

    public void checkGpsPermission() {
        if (ActivityCompat.checkSelfPermission(Map2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("TEST_TAG", "checkGpsPermission 1");
            isLocationServiceEnabled(Map2Activity.this);
        } else {
            Log.e("TEST_TAG", "checkGpsPermission 2");
            requestLocationPermission();
        }
    }

    public void isLocationServiceEnabled(Activity context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show();

        } else {
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng pos = marker.getPosition();
        String loc = marker.getTitle();
        latitude = pos.latitude;
        longitude = pos.longitude;
        return false;
    }

    @Override
    public void locationDetectedSuccess(double latitude, double longitude) {

        if (mMap != null) {
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
        }


    }
    private Location lastLocation;
    private Marker currentLocationMarker;
    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        /*if(client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }*/

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationDetector locationDetector = new LocationDetector(Map2Activity.this, Map2Activity.this, getSupportFragmentManager(), googleMap);
        locationDetector.detectLocation();

        LatLng defaultPos = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPos, 17.5f));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                mTimerIsRunning = true;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (mTimerIsRunning) {

                    CameraPosition cameraPosition = mMap.getCameraPosition();
                    Log.d("Camera postion change" + "", cameraPosition + "");
                    mMap.clear();

                    try {

                        latitude = cameraPosition.target.latitude;
                        longitude = cameraPosition.target.longitude;


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mTimerIsRunning = false;
                }
            }
        });


       /* if (ActivityCompat.checkSelfPermission(Act_Map2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Act_Map2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(false);*/
    }
}
