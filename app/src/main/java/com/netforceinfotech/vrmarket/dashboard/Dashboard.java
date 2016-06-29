package com.netforceinfotech.vrmarket.dashboard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.general.AboutFragment;
import com.netforceinfotech.vrmarket.general.ContactFragment;
import com.netforceinfotech.vrmarket.general.PrivacyPolicyFragment;
import com.netforceinfotech.vrmarket.search.SearchActivity;

public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private DashboardFragment dashboardFragment;
    AboutFragment aboutFragment;
    ContactFragment contactFragment;
    PrivacyPolicyFragment privacyPolicyFragment;

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
        final PrimaryDrawerItem about = new PrimaryDrawerItem().withName(R.string.about);
        PrimaryDrawerItem contact = new PrimaryDrawerItem().withName(R.string.contact);
        final PrimaryDrawerItem privacy = new PrimaryDrawerItem().withName(R.string.privacy);
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
                        switch (position) {
                            case 0:
                                setupDashboard();
                                break;
                            case 1:
                                setupAbout();
                                break;
                            case 2:
                                setupContact();
                                break;
                            case 3:
                                setupPrivacyPolicy();
                                break;
                            default:
                                setupDashboard();

                        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment, tag);
        transaction.commit();
    }

    private void setupDashboard() {
        dashboardFragment = new DashboardFragment();
        String tagName = dashboardFragment.getClass().getName();
        replaceFragment(dashboardFragment, tagName);
    }

    private void setupAbout() {
        aboutFragment = new AboutFragment();
        String tagName = aboutFragment.getClass().getName();
        replaceFragment(aboutFragment, tagName);
    }

    private void setupContact() {
        contactFragment = new ContactFragment();
        String tagName = contactFragment.getClass().getName();
        replaceFragment(contactFragment, tagName);
    }

    private void setupPrivacyPolicy() {
        privacyPolicyFragment = new PrivacyPolicyFragment();
        String tagName = privacyPolicyFragment.getClass().getName();
        replaceFragment(privacyPolicyFragment, tagName);
    }
}
