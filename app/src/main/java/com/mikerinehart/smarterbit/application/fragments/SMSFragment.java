package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.mikerinehart.smarterbit.R;

public class SMSFragment extends PreferenceFragment {

    private CheckBoxPreference mSMSEnabledCheckBox;
    private Preference mTestSMSPreference;
    private CheckBoxPreference mNotifyScreenOnCheckBox;

    private OnFragmentInteractionListener mListener;

    public static SMSFragment newInstance() {
        SMSFragment fragment = new SMSFragment();
        return fragment;
    }

    public SMSFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sms_preferences);

        mSMSEnabledCheckBox = (CheckBoxPreference)findPreference("smsEnabled");
        mTestSMSPreference = (Preference)findPreference("smsTestNotification");
        mNotifyScreenOnCheckBox = (CheckBoxPreference)findPreference("smsScreenOffOnly");

        mTestSMSPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Test notification sent - check your FitBit!", Toast.LENGTH_SHORT).show();
                //TODO: Add in Test function
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("SMS Notifications");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }

}
