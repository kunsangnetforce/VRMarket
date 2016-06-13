package com.netforceinfotech.vrmarket.dashboard.app;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.dashboard.general.MyCustomAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment implements View.OnClickListener {
    LinearLayout linearLayoutLeft, linearLayoutRight;
    private LinearLayoutManager layoutManager;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    ArrayList<RowData> rowDatas = new ArrayList<>();
    private View view;
    private MaterialBetterSpinner category, free, latest;

    public AppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_app, container, false);
        linearLayoutLeft = (LinearLayout) view.findViewById(R.id.linearLeft);
        linearLayoutRight = (LinearLayout) view.findViewById(R.id.linearRight);
        linearLayoutRight.setOnClickListener(this);
        linearLayoutLeft.setOnClickListener(this);
        setupRecycle();
        setupDropDown(view);
        return view;

    }

    private void setupDropDown(View view) {
        String[] categoryList = {"All Category", "Category 1", "Category 2", "Category 3", "Category 4"};
        String[] sortbyList = {"Latest", "Alphabet", "App size", "Popularity", "Most Downloaded"};
        String[] filterPricListe = {"Free", "Paid"};
        MyCustomAdapter adapter1 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, categoryList);
        MyCustomAdapter adapter2 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, sortbyList);
        MyCustomAdapter adapter3 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, filterPricListe);
        category = (MaterialBetterSpinner) view.findViewById(R.id.category);
        category.setAdapter(adapter1);
        category.setText(adapter1.getItem(0));

        latest = (MaterialBetterSpinner) view.findViewById(R.id.latest);
        latest.setAdapter(adapter2);
        latest.setText(adapter2.getItem(0));
        free = (MaterialBetterSpinner) view.findViewById(R.id.free);
        free.setAdapter(adapter3);
        free.setText(adapter3.getItem(0));

    }

    private void setupRecycle() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(context, rowDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLeft:
                try {
                    recyclerView.scrollToPosition(RecyclerViewAdapter.position - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                break;
            case R.id.linearRight:
                try {
                    recyclerView.scrollToPosition(RecyclerViewAdapter.position + 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }


}
