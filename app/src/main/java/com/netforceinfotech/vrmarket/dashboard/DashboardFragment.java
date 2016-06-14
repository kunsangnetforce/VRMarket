package com.netforceinfotech.vrmarket.dashboard;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.dashboard.general.CustomViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    private View view;
    private Context context;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context = getActivity();
        setupTab();
        return view;
    }

    private void setupTab() {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Apps"));
        tabLayout.addTab(tabLayout.newTab().setText("Games"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.pager);
        viewPager.setPagingEnabled(false);
        final PagerAdapter adapter = new PagerAdapter
                (((AppCompatActivity) context).getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
