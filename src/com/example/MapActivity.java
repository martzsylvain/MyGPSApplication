package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/**
 * Created with IntelliJ IDEA.
 * User: kiki
 * Date: 31/05/12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends com.google.android.maps.MapActivity {
    private MapView mMapView;
    private MapController mMapController;
    private MyLocationOverlay mMyLocation = null;
    private Intent mIntentForLocationService = null;
    private Pushpin mPushpin;

    /*
    * Mes lignes
    * De commentaire
    * Sur la methode
    * */
    private BroadcastReceiver mLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra(LocationService.LOCATION_KEY_LAT) && intent.hasExtra(LocationService.LOCATION_KEY_LNG)) {
                mMapController.setCenter(new GeoPoint(
                        (int) (intent.getDoubleExtra(LocationService.LOCATION_KEY_LAT, 0.) * 1000000),
                        (int) (intent.getDoubleExtra(LocationService.LOCATION_KEY_LNG, 0.) * 1000000)));
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymapview);
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.setBuiltInZoomControls(true);

        /* initialisation map controller  & ma overlay de ma position */
        mMapController = mMapView.getController();
        mMyLocation = new MyLocationOverlay(this, mMapView);

        /* initialisation de l'image pour les pushpins */
        Drawable marker = getResources().getDrawable(R.drawable.pushpin);
        marker.setBounds(0, -marker.getIntrinsicHeight() / 4, marker.getIntrinsicWidth() / 4, 0);
        mPushpin = new Pushpin(marker, mMapView, this);

        GeoPoint point = new GeoPoint(44830552, -580902);
        mPushpin.addItem(point, "Laissez les bon temps rouler!", "I'm in Louisiana!");
        mMapView.getOverlays().add(mPushpin);

        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                GeoPoint point = new GeoPoint(45830552, -590902);
                mPushpin.addItem(point, "trololo", "I'm in trololo!");
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mIntentForLocationService == null) {
            mIntentForLocationService = new Intent(MapActivity.this, LocationService.class);
            startService(mIntentForLocationService);
        }

        mMyLocation.enableMyLocation();
        mMyLocation.enableCompass();

        mMapView.getOverlays().add(mMyLocation);
        registerReceiver(mLocationReceiver, new IntentFilter(LocationService.LOCATION_KEY));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int resultCode = RESULT_CANCELED;
            Intent intent = null;

            if (mMyLocation != null && mMyLocation.getLastFix() != null) {
                intent = new Intent();
                intent.putExtra(LocationService.LOCATION_KEY_LNG, mMyLocation.getLastFix().getLongitude());
                intent.putExtra(LocationService.LOCATION_KEY_LAT, mMyLocation.getLastFix().getLatitude());

                if (mIntentForLocationService != null)
                    stopService(mIntentForLocationService);

                resultCode = RESULT_OK;
            }

            this.unregisterReceiver(this.mLocationReceiver);

            setResult(resultCode, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}