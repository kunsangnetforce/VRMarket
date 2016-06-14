package com.netforceinfotech.vrmarket.dashboard.game;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.dashboard.game.commom.RecyclerViewAdapterC;
import com.netforceinfotech.vrmarket.dashboard.game.commom.RowDataC;
import com.netforceinfotech.vrmarket.dashboard.game.featured.RecyclerViewAdapterF;
import com.netforceinfotech.vrmarket.dashboard.game.featured.RowDataF;
import com.netforceinfotech.vrmarket.dashboard.general.MyCustomAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {
    LinearLayout linearLayoutLeft, linearLayoutRight;
    private LinearLayoutManager layoutManagerFeatured;
    private Context context;
    private RecyclerView recyclerView_Featured;
    private RecyclerViewAdapterF adapterFeatured;
    ArrayList<RowDataF> rowDataFs = new ArrayList<>();
    private View view;
    private MaterialBetterSpinner category, free, latest;
    private RecyclerView recyclerView_Commom;
    private RecyclerViewAdapterC adapterCommom;
    private ArrayList<RowDataC> rowDatasCC =new ArrayList<>();
    private LinearLayoutManager layoutManagerCommom;

    public GameFragment() {
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
        setupRecycleFeatured();
        setupRecycleCommom();
        setupDropDown(view);
        return view;

    }

    private void setupDropDown(View view) {
        String[] categoryList = {"All Category", "Category 1", "Category 2", "Category 3", "Category 4"};
        String[] sortbyList = {"Latest", "Alphabet", "App size", "Popularity", "Most Downloaded"};
        String[] filterPricListe = {"Free", "Paid","All"};
        MyCustomAdapter adapter1 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, categoryList);
        MyCustomAdapter adapter2 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, sortbyList);
        MyCustomAdapter adapter3 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, filterPricListe);
        category = (MaterialBetterSpinner) view.findViewById(R.id.category);
        category.setAdapter(adapter1);
        category.setHint(getResources().getString(R.string.category));

        latest = (MaterialBetterSpinner) view.findViewById(R.id.latest);
        latest.setAdapter(adapter2);
        latest.setHint(getResources().getString(R.string.sort));
        free = (MaterialBetterSpinner) view.findViewById(R.id.free);
        free.setAdapter(adapter3);
        free.setHint(getResources().getString(R.string.price));

    }

    private void setupRecycleFeatured() {
        recyclerView_Featured = (RecyclerView) view.findViewById(R.id.recyclerFeatured);
        layoutManagerFeatured = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Featured.setLayoutManager(layoutManagerFeatured);
        adapterFeatured = new RecyclerViewAdapterF(context, rowDataFs);
        recyclerView_Featured.setAdapter(adapterFeatured);
        recyclerView_Featured.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
    }

    private void setupRecycleCommom() {
        recyclerView_Commom = (RecyclerView) view.findViewById(R.id.recyclerCommom);
        layoutManagerCommom = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView_Commom.setLayoutManager(layoutManagerCommom);
        adapterCommom = new RecyclerViewAdapterC(context, rowDatasCC);
        recyclerView_Commom.setAdapter(adapterCommom);
        recyclerView_Commom.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLeft:
                try {
                    recyclerView_Featured.scrollToPosition(RecyclerViewAdapterF.position - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                break;
            case R.id.linearRight:
                try {
                    recyclerView_Featured.scrollToPosition(RecyclerViewAdapterF.position + 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }


}
