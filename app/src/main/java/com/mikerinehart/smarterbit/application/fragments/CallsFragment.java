package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {}

}
