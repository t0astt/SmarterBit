package com.mikerinehart.smarterbit.application.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikerinehart.smarterbit.R;

public class OtherFragment extends BasePreferenceFragment {

    public static OtherFragment newInstance() {
        return new OtherFragment();
    }

    public OtherFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.other_preferences);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().setTitle(getString(R.string.other_title));
    }

    public interface OnFragmentInteractionListener {}

}
