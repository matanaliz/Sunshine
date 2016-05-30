package com.udacity.matanaliz.sunshine;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SunshineFragment extends Fragment {

    private class DownloadForecastTask extends AsyncTask<String, Void, String> {

        // Nice way to get class name for a tag!
        private final String LOG_TAG = DownloadForecastTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... location) {

            // Nothing to do here, without params
            if (location.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String apiKey = "0186af915d4ac7620da24230fcdf8491";
            String forecastJsonStr = null;

            try {

                final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String UNITS_PARAM = "units";
                final String DAY_COUNT_PARAM = "cnt";
                final String APP_KEY_PARAM = "appid";


                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, location[0])
                    .appendQueryParameter(UNITS_PARAM, "metric")
                    .appendQueryParameter(DAY_COUNT_PARAM, Integer.toString(7))
                    .appendQueryParameter(APP_KEY_PARAM, apiKey).build();

                Log.v(LOG_TAG, "Built Uri " + builtUri);

                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ( (line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Exception caught ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return forecastJsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(LOG_TAG, "Forecast fetched:\n " + result);
        }
    }

    private final String LOG_TAG = SunshineFragment.class.getSimpleName();
    
    public SunshineFragment() {
    }

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
            new DownloadForecastTask().execute("London,uk");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);

        String forecastJson = null;

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

//            forecastJson = getForecastString("kiev,ua");
        } else {
            Log.e(LOG_TAG, "Network is unavailable.");
        }

        String [] forecastArray = {
                "Today - Rain - 22",
                "Tomorrow - Rain - 21",
                "Sunday - Rain - 20",
                "Monday - Rain - 21",
                "Tuesday - Rain - 21",
                "Wednesday - Rain - 21",
                "Thursday - Rain - 21",
                "Friday - Sunny - 23"
        };

        List<String> itemList = new ArrayList<String>(
                Arrays.asList(forecastArray));

        ArrayAdapter<String> forecastAdapter =
                new ArrayAdapter<String>(
                        // Context of the Activity
                        getActivity(),
                        // Name of layout file
                        R.layout.list_item_forecast,
                        // Id of view inside of layout xml file
                        R.id.list_item_forecast_textview,
                        // Data
                        forecastArray);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);

        return rootView;
    }
}
