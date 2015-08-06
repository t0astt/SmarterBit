package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mikerinehart.smarterbit.R;

public class CallsFragment extends PreferenceFragment {

    public static CallsFragment newInstance() {
        CallsFragment fragment = new CallsFragment();
        return fragment;
    }

    public CallsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.call_preferences);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle("Call Notifications");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {}

}
