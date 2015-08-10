package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mikerinehart.smarterbit.R;

public class ExtrasFragment extends BasePreferenceFragment {

    public static ExtrasFragment newInstance() {
        return new ExtrasFragment();
    }

    public ExtrasFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.extras_preferences);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.extras_title));
    }

    public interface OnFragmentInteractionListener {}

}
