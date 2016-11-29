package com.udacity.matanaliz.sunshine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private String mLocation;
    private static final String FORECASTFRAGMENT_TAG = "FFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLocation = Utility.getPreferredLocation(this);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment, new SunshineFragment(), FORECASTFRAGMENT_TAG)
//                    .commit();
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String newLocation = Utility.getPreferredLocation(this);
        if (newLocation != null && newLocation.equals(mLocation)) {
            SunshineFragment sf = (SunshineFragment)getSupportFragmentManager()
                    .findFragmentByTag(FORECASTFRAGMENT_TAG);
            if (sf != null) {
                sf.onLocationChanged();
            }
            mLocation = newLocation;
        }
    }
}
