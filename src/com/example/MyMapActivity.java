package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.*;

/**
 * Created with IntelliJ IDEA.
 * User: kiki
 * Date: 31/05/12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class MyMapActivity extends MapActivity {
    private MapView mMapView;
    private MapController mMapController;
    private GeoPoint mMyPosition;
    private MyLocationOverlay myLocation = null;
    private Intent mIntentForLocationService = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymapview);

        String coordinates[] = {"44.833", "-0.567"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);

        mMyPosition = new GeoPoint(
                (int) (lat * 1E4),
                (int) (lng * 1E4));
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.setBuiltInZoomControls(true);

        mMapController = mMapView.getController();

        myLocation = new MyLocationOverlay(this, mMapView);
        myLocation.enableMyLocation();
        myLocation = new MyLocationOverlay(getApplicationContext(), mMapView);
        myLocation.runOnFirstFix(new Runnable() {
            public void run() {
                mMapController.animateTo(myLocation.getMyLocation());
                mMapController.setZoom(14);
            }
        });
        mMapView.getOverlays().add(myLocation);

        Log.v("toto",String.valueOf(myLocation.getMyLocation()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIntentForLocationService = new Intent(MyMapActivity.this, MyLocationService.class);
        if (mIntentForLocationService != null)
            startService(mIntentForLocationService);
        myLocation.enableMyLocation();
        myLocation.enableCompass();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        stopService(mIntentForLocationService);
        finish();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

}