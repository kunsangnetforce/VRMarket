package com.netforceinfotech.vrmarket.app_detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;

import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RecyclerViewAdapterS;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RowDataS;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class AppDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView_Same;
    private Context context;
    private LinearLayoutManager layoutManagerSame;
    private RecyclerViewAdapterS adapterSame;
    private ArrayList<RowDataS> rowDataS = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        context = this;
        setupToolBar();
        setupRecycleFeatured();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupRecycleFeatured() {
        recyclerView_Same = (RecyclerView) findViewById(R.id.recyclerFeatured);
        layoutManagerSame = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Same.setLayoutManager(layoutManagerSame);
        adapterSame = new RecyclerViewAdapterS(context, rowDataS);
        recyclerView_Same.setAdapter(adapterSame);
        recyclerView_Same.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("App title");;

    }
}
