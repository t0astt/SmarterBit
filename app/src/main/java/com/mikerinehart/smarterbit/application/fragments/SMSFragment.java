package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.widget.Toast;

import com.mikerinehart.smarterbit.R;

public class SMSFragment extends BasePreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static SMSFragment newInstance() {
        return new SMSFragment();
    }

    public SMSFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sms_preferences);

        Preference mTestNotificationPreference = findPreference("smsTestNotification");

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
    public void onSharedPreferenceChanged(SharedPreferences sp, String s) {
        super.onSharedPreferenceChanged(sp, s);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.sms_title));
    }

    public interface OnFragmentInteractionListener {}

}
