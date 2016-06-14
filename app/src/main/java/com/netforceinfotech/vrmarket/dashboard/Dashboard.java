package com.netforceinfotech.vrmarket.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.netforceinfotech.vrmarket.R;

public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupToolBar();
        setupNavigationDrawer();
        setupDashboard();

    }


    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setupNavigationDrawer() {
        PrimaryDrawerItem home = new PrimaryDrawerItem().withName(R.string.home);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withName(R.string.about);
        PrimaryDrawerItem contact = new PrimaryDrawerItem().withName(R.string.contact);
        PrimaryDrawerItem privacy = new PrimaryDrawerItem().withName(R.string.privacy);
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home, about, contact, privacy
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment, tag);
        transaction.commit();
    }
    private void setupDashboard() {
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
        }
        String tagName = dashboardFragment.getClass().getName();
        replaceFragment(dashboardFragment, tagName);
    }
}
