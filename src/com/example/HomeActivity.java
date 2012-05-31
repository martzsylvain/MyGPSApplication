package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity implements View.OnClickListener {
    private Button mButtonStartMapActivity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mButtonStartMapActivity = (Button) findViewById(R.id.startButton);
        mButtonStartMapActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intentForMapActivity = null;
        final int viewId = view.getId();
        if (viewId == R.id.startButton)
        {
            intentForMapActivity = new Intent(HomeActivity.this, MyMapActivity.class);
        }
        if (intentForMapActivity != null)
            startActivity(intentForMapActivity);
    }
}
