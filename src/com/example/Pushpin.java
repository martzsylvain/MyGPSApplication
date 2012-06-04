package com.example;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kiki
 * Date: 04/06/12
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class Pushpin extends ItemizedOverlay<OverlayItem> {
    private ArrayList<OverlayItem> mOverlayItemList;

    public Pushpin(Drawable drawable) {
        super(drawable);
        mOverlayItemList = new ArrayList<OverlayItem>();

        populate();
    }

    public void addItem(GeoPoint p, String title, String snippet) {
        OverlayItem newItem = new OverlayItem(p, title, snippet);
        mOverlayItemList.add(newItem);
        populate();
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean b) {
        super.draw(canvas, mapView, b);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlayItemList.get(i);
    }

    @Override
    public int size() {
        return mOverlayItemList.size();
    }

    @Override
    public boolean onTap(GeoPoint p, MapView mapView) {
//        String title = "pt:" + String.valueOf(mOverlayItemList.size() + 1);
//        String snippet = "geo:\n"
//                + String.valueOf(p.getLatitudeE6()) + "\n"
//                + String.valueOf(p.getLongitudeE6());

//        addItem(p, title, snippet);

        return true;
    }
}
