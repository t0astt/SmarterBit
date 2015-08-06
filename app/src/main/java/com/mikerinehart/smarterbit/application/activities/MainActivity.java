package com.mikerinehart.smarterbit.application.activities;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikerinehart.smarterbit.R;
import com.mikerinehart.smarterbit.application.fragments.CallsFragment;
import com.mikerinehart.smarterbit.application.fragments.HomeFragment;
import com.mikerinehart.smarterbit.application.fragments.SMSFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
                HomeFragment.OnFragmentInteractionListener,
                CallsFragment.OnFragmentInteractionListener,
                SMSFragment.OnFragmentInteractionListener {

    Drawer mResult;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Calls").withIcon(FontAwesome.Icon.faw_phone),
                        new PrimaryDrawerItem().withName("SMS").withIcon(FontAwesome.Icon.faw_envelope_o),
                        new PrimaryDrawerItem().withName("Other").withIcon(FontAwesome.Icon.faw_circle_o),
                        new PrimaryDrawerItem().withName("Extras").withIcon(FontAwesome.Icon.faw_tasks),
                        new PrimaryDrawerItem().withName("About").withIcon(FontAwesome.Icon.faw_info)
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
                                    ft.addToBackStack("Home");
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (1):
                                    //Calls
                                    ft.replace(R.id.container, CallsFragment.newInstance());
                                    ft.addToBackStack("Calls");
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (2):
                                    //SMS
                                    ft.replace(R.id.container, SMSFragment.newInstance());
                                    ft.addToBackStack("SMS");
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (3):
                                    //Other
                                    ft.replace(R.id.container, HomeFragment.newInstance());
                                    ft.addToBackStack("Home");
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (4):
                                    //Extras
                                    ft.replace(R.id.container, HomeFragment.newInstance());
                                    ft.addToBackStack("Home");
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.commit();
                                    break;
                                case (5):
                                    //About & Legal
                                    ft.replace(R.id.container, HomeFragment.newInstance());
                                    ft.addToBackStack("Home");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Testing", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){}
}
