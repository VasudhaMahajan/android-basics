package com.s1.gps_geolocator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap,mMap1,mMap2,mMap3;
    LocationManager locationmanager;
    private GPSTracker gpsTracker;
    Location mlastLocation;
    SupportMapFragment mapFragment;
    LatLng latlag;
    int x;
    Circle circle;
    Marker marker1,marker2;
    Polyline line;
    String str1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent in = getIntent();
        str1= in.getExtras().getString("first");

        locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        else
        {

        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //check network provider is enabled
        if(locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    //get Latitude
                    double latitude=location.getLatitude();
                    //get lognitude
                    double longitude=location.getLongitude();
                    //Instantiate the class, LatLng
                    latlag=new LatLng(latitude, longitude);
                    Geocoder geocoder= new Geocoder(getApplicationContext());

                    try {
                        List<android.location.Address>addresslist = geocoder.getFromLocation(latitude, longitude,1);
                        String str= addresslist.get(0).getLocality()+",";
                        str+= addresslist.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latlag).title(str));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlag));
  //                      circle=drawCircle(new LatLng(latitude,longitude));


                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlag, 18));
                        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        //else use GPS provider
        else if(locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get Latitude
                    double latitude=location.getLatitude();
                    //get lognitude
                    double longitude=location.getLongitude();
                    //Instantiate the class, LatLng
                     latlag=new LatLng(latitude, longitude);
                    Geocoder geocoder= new Geocoder(getApplicationContext());

                    try {
                        List<android.location.Address>addresslist = geocoder.getFromLocation(latitude, longitude,1);
                        String str= addresslist.get(0).getLocality()+",";
                        str+= addresslist.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latlag).title(str));
//                        circle=drawCircle(new LatLng(latitude,longitude));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlag, 18));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }


    }
//
//    private Circle drawCircle(LatLng latLng) {
//        CircleOptions options=new CircleOptions().center(latLng)
//                .radius(70).fillColor(0x55000000).strokeColor(Color.BLUE).strokeWidth(1);
//
//        return mMap.addCircle(options);
//    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {


            return true;
        }
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
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap1 = googleMap;
        mMap2 = googleMap;
        mMap3 = googleMap;
        if (str1.equals("1")) {
            LatLng Cummins = new LatLng(18.4875, 73.8147);
            mMap1.addMarker(new MarkerOptions().position(Cummins).title("Baya Karve"));
            mMap1.moveCamera(CameraUpdateFactory.newLatLng(Cummins));
            LatLng YashLaxmi = new LatLng(18.4870567, 73.818947);
            mMap2.addMarker(new MarkerOptions().position(YashLaxmi).title("YashLaxmi"));
            mMap2.moveCamera(CameraUpdateFactory.newLatLng(YashLaxmi));
            LatLng Divkar = new LatLng(18.4852, 73.8159);
            mMap3.addMarker(new MarkerOptions().position(Divkar).title("Divkar"));
            mMap3.moveCamera(CameraUpdateFactory.newLatLng(Divkar));
        }
       else if (str1.equals("2")) {
            LatLng Cummins = new LatLng(18.4875, 73.8147);
            mMap1.addMarker(new MarkerOptions().position(Cummins).title("Baya Karve"));
            mMap1.moveCamera(CameraUpdateFactory.newLatLng(Cummins));
        }
        else if (str1.equals("3")) {
            LatLng YashLaxmi = new LatLng(18.4870567, 73.818947);
            mMap2.addMarker(new MarkerOptions().position(YashLaxmi).title("YashLaxmi"));
            mMap2.moveCamera(CameraUpdateFactory.newLatLng(YashLaxmi));
        }
        else if (str1.equals("4")) {
            LatLng Divkar = new LatLng(18.4852, 73.8159);
            mMap3.addMarker(new MarkerOptions().position(Divkar).title("Divkar"));
            mMap3.moveCamera(CameraUpdateFactory.newLatLng(Divkar));
        }
//        String url = getMapsApiDirectionsUrl(latlag,sydney);
//        ReadTask
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,1.0f));  //zoom in
    }


}