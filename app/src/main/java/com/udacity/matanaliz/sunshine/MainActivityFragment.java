package com.udacity.matanaliz.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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
