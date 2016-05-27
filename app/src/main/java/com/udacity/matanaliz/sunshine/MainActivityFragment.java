package com.udacity.matanaliz.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        String [] itemsArray = {
                "Today - Rain - 22",
                "Tomorrow - Rain - 21",
                "Sunday - Rain - 20",
                "Monday - Rain - 21"
        };

        List<String> itemList = new ArrayList<String>(
                Arrays.asList(itemsArray));

        return rootView;
    }
}
