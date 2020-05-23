package ru.mkedonsky.myapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
public class MyLocationListener implements LocationListener {

    static Location imHere;

    static void SetUpLocationListener(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        assert locationManager != null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                0,
                locationListener);

        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//        if (imHere == null) {
//            locationManager.requestLocationUpdates();
//        }

    }


    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
