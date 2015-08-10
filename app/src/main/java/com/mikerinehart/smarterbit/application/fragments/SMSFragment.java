package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.mikerinehart.smarterbit.R;
import com.mikerinehart.smarterbit.xposed.Common;
import com.mikerinehart.smarterbit.xposed.SMS;
import com.mikerinehart.smarterbit.xposed.SmarterBitXposed;

import java.util.Map;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
