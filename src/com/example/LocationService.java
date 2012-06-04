package com.example;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: kiki
 * Date: 31/05/12
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class LocationService extends Service implements LocationListener {

    public static final String LOCATION_KEY = "myKey";
    public static final String LOCATION_KEY_LAT = LOCATION_KEY + ".lat";
    public static final String LOCATION_KEY_LNG = LOCATION_KEY + ".lng";

    private LocationManager mLocationManager;

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);    //To change body of overridden methods use File | Settings | File Templates.
        if (mLocationManager == null)
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(200);
        mLocationManager.getProviders(criteria, true);

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            Log.v("GPSStatus", "GPS disabled");
        else
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 35, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        if (mLocationManager != null && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationManager.removeUpdates(this);
            mLocationManager = null;
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Intent intent = new Intent(LOCATION_KEY);
            intent.putExtra(LOCATION_KEY_LNG, location.getLongitude());
            intent.putExtra(LOCATION_KEY_LAT, location.getLatitude());
            sendBroadcast(intent);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(LocationService.this, "Status changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(LocationService.this, "GPS Turned on", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(LocationService.this, "GPS Turned off", Toast.LENGTH_SHORT).show();
    }
}
