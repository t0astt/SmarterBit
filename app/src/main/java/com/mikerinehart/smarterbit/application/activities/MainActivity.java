package com.mikerinehart.smarterbit.application.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikerinehart.smarterbit.R;
import com.mikerinehart.smarterbit.application.fragments.AboutFragment;
import com.mikerinehart.smarterbit.application.fragments.CallsFragment;
import com.mikerinehart.smarterbit.application.fragments.ExtrasFragment;
import com.mikerinehart.smarterbit.application.fragments.HomeFragment;
import com.mikerinehart.smarterbit.application.fragments.OtherFragment;
import com.mikerinehart.smarterbit.application.fragments.SMSFragment;

public class MainActivity extends AppCompatActivity {

    private Drawer mResult;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.app_name)).withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName(getString(R.string.call_title)).withIcon(FontAwesome.Icon.faw_phone),
                        new PrimaryDrawerItem().withName(getString(R.string.sms_title)).withIcon(FontAwesome.Icon.faw_envelope_o),
                        new PrimaryDrawerItem().withName(getString(R.string.other_title)).withIcon(FontAwesome.Icon.faw_circle_o),
                        new PrimaryDrawerItem().withName(getString(R.string.extras_title)).withIcon(FontAwesome.Icon.faw_tasks),
                        new PrimaryDrawerItem().withName(getString(R.string.about_title)).withIcon(FontAwesome.Icon.faw_info)
                )
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            switch (position) {
                                case (0):
                                    //Home
                                    ft.replace(R.id.container, HomeFragment.newInstance());
                                    ft.addToBackStack(getString(R.string.app_name));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (1):
                                    //Calls
                                    ft.replace(R.id.container, CallsFragment.newInstance());
                                    ft.addToBackStack(getString(R.string.call_title));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (2):
                                    //SMS
                                    ft.replace(R.id.container, new SMSFragment());
                                    ft.addToBackStack(getString(R.string.sms_title));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (3):
                                    //Other
                                    ft.replace(R.id.container, OtherFragment.newInstance());
                                    ft.addToBackStack(getString(R.string.other_title));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (4):
                                    //Extras
                                    ft.replace(R.id.container, ExtrasFragment.newInstance());
                                    ft.addToBackStack(getString(R.string.extras_title));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (5):
                                    //About & Legal
                                    ft.replace(R.id.container, AboutFragment.newInstance());
                                    ft.addToBackStack(getString(R.string.about_title));
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                default:
                                    break;
                            }
                            mResult.closeDrawer();
                        }
                        return true;
                    }
                })
                .build();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commit();
    }
}
