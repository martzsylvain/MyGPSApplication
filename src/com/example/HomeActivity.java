package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity implements View.OnClickListener {
    private Button mButtonStartMapActivity;
    private Button mButtonQuitApplication;
    private TextView mInfoTextView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mButtonStartMapActivity = (Button) findViewById(R.id.startButton);
        mButtonQuitApplication = (Button) findViewById(R.id.quitButton);
        mInfoTextView = (TextView) findViewById(R.id.textViewInfo);

        mButtonStartMapActivity.setOnClickListener(this);
        mButtonQuitApplication.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intentForMapActivity = null;
        final int viewId = view.getId();
        if (viewId == R.id.startButton)
            intentForMapActivity = new Intent(HomeActivity.this, MapActivity.class);
        else if(viewId == R.id.quitButton)
            finish();

        if (intentForMapActivity != null)
            startActivityForResult(intentForMapActivity, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if(resultCode == RESULT_OK && data != null)     {

               String info = String.format("latitude : %.5f\nlongitude : %.5f",
                       data.getDoubleExtra(LocationService.LOCATION_KEY_LAT, 0.),
                       data.getDoubleExtra(LocationService.LOCATION_KEY_LNG, 0.));
                mInfoTextView.setText(info);
            }
            else if(resultCode == RESULT_CANCELED)
                mInfoTextView.setText("data unavailable");
        }
    }
}
