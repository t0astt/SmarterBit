package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mikerinehart.smarterbit.R;

public class SMSFragment extends PreferenceFragment {

    public static SMSFragment newInstance() {
        return new SMSFragment();
    }

    public SMSFragment() {}

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.sms_preferences);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.sms_title));
    }
}
