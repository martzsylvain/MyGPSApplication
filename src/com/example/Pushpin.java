package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.maps.*;

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
    private MapView mMapView;
    private LinearLayout mPopup;
    private Context mContext;

    public Pushpin(Drawable drawable, MapView view, Context context) {
        super(drawable);
        mOverlayItemList = new ArrayList<OverlayItem>();
        mMapView = view;
        mContext = context;
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
    protected boolean onTap(int i) {

        if (mOverlayItemList != null && !mOverlayItemList.isEmpty()) {

            /* Recuperation des information de mon pin ( title, details & GeoPoint ) */
            OverlayItem item = mOverlayItemList.get(i);
            String title = item.getTitle();
            String details = item.getSnippet();
            GeoPoint point = item.getPoint();

            /**********************************************************************************************************/

            /* Instantiation & Initialisation de ma vue (popup) sur le context de la map */
            if (mPopup == null)
            {
                mPopup = (LinearLayout) ((MapActivity) mContext).getLayoutInflater().inflate(R.layout.popup, null);
                mPopup.setBackgroundResource(android.R.color.black);
            }

            mPopup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            /* set information on popup layout */
            ((TextView) mPopup.findViewById(R.id.title)).setText(title);
            ((TextView) mPopup.findViewById(R.id.details)).setText(details);
            ((TextView) mPopup.findViewById(R.id.coordinate)).setText("\nLatitude : " + point.getLatitudeE6() / 1E6 + "\nLongitude : "+ point.getLongitudeE6() / 1E6);
            ((Button) mPopup.findViewById(R.id.remove)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            /**********************************************************************************************************/

            /* set popup coord on mapView*/
            Projection projection = mMapView.getProjection();
            Point pointMap = new Point();
            projection.toPixels(point, pointMap);

            int x = (int) (mPopup.getWidth());
            int y = (int) (-35);

            MapView.LayoutParams lp = new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, point, x, y, MapView.LayoutParams.CENTER);

            mMapView.removeView(mPopup);

            mMapView.addView(mPopup, lp);
            mMapView.invalidate();

        }
        return super.onTap(i);
    }
}
