package com.udacity.matanaliz.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
