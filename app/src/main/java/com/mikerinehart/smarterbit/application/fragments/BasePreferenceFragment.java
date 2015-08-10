package com.mikerinehart.smarterbit.application.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;

import com.mikerinehart.smarterbit.xposed.SmarterBitXposed;

/**
 * Created by mike on 8/9/15.
 */
public class BasePreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        //SmarterBitXposed.reloadPreferences();
    }
}
