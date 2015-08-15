package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mikerinehart.smarterbit.R;

public class CallsFragment extends BasePreferenceFragment {

    public static CallsFragment newInstance() {
        return new CallsFragment();
    }

    public CallsFragment() {}

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.call_preferences);

        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.call_title));
    }
}
