package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.mikerinehart.smarterbit.R;

public class CallsFragment extends PreferenceFragment {

    public static CallsFragment newInstance() {
        return new CallsFragment();
    }

    public CallsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.call_preferences);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        Preference mTestNotificationPreference = findPreference("callTestNotification");

        mTestNotificationPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(),
                        getString(R.string.test_notification_sent),
                        Toast.LENGTH_SHORT).show();
                //TODO: Add in Test function
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.call_title));
    }

    public interface OnFragmentInteractionListener {}

}
