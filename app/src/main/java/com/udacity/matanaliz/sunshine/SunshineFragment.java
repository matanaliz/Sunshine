package com.udacity.matanaliz.sunshine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SunshineFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    private final String LOG_TAG = SunshineFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            updateWeather();
            return true;
        }
        if (id == R.id.action_settings) {

            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_location) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default_value));

            Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                    .appendQueryParameter("q", location)
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(LOG_TAG, "Unable to start implicit intent to open map for location" + location);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String currentLocation = settings.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default_value));

        FetchWeatherTask task = new FetchWeatherTask(getContext(), mForecastAdapter);

        Toast toast = Toast.makeText(getActivity(), currentLocation, Toast.LENGTH_SHORT);
        toast.show();

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            task.execute(currentLocation);
        } else {
            Log.e(LOG_TAG, "Network is unavailable.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mForecastAdapter =
                new ArrayAdapter<String>(
                        // Context of the Activity
                        getActivity(),
                        // Name of layout file
                        R.layout.list_item_forecast,
                        // Id of view inside of layout xml file
                        R.id.list_item_forecast_textview,
                        new ArrayList<String>());

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = mForecastAdapter.getItem(position);
                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, text);

                startActivity(intent);
            }
        });

        return rootView;
    }
}
