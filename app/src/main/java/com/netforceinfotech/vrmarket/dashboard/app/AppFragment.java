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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.dashboard.app.commom.RecyclerViewAdapterC;
import com.netforceinfotech.vrmarket.dashboard.app.commom.RowDataC;
import com.netforceinfotech.vrmarket.dashboard.app.featured.RecyclerViewAdapterF;
import com.netforceinfotech.vrmarket.dashboard.app.featured.RowDataF;
import com.netforceinfotech.vrmarket.dashboard.general.MyCustomAdapter;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends Fragment implements View.OnClickListener {
    MaterialRippleLayout rippleLeft, rippleRight;
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
    private ArrayList<RowDataC> rowDatasCC = new ArrayList<>();
    private LinearLayoutManager layoutManagerCommom;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    // private SwipyRefreshLayout mSwipyRefreshLayout;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    private JsonObject jsonObject;

    public AppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_app, container, false);
        rippleLeft = (MaterialRippleLayout) view.findViewById(R.id.rippleleft);
        rippleRight = (MaterialRippleLayout) view.findViewById(R.id.rippleright);
        linearLayoutLeft = (LinearLayout) view.findViewById(R.id.linearLeft);
        linearLayoutRight = (LinearLayout) view.findViewById(R.id.linearRight);
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                getData(context, "apps");
            }
        });
        getData(context, "apps");
        linearLayoutLeft.setOnClickListener(this);
        linearLayoutRight.setOnClickListener(this);
        rippleRight.setOnClickListener(this);
        rippleLeft.setOnClickListener(this);
        setupRecycleFeatured();
        setupRecycleCommom();
        setupDropDown(view);
        return view;

    }

    private void getData(Context context, String type) {
        String url = getResources().getString(R.string.url);
        Ion.with(context)
                .load(url)
                .setBodyParameter("type", type)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        mSwipyRefreshLayout.setRefreshing(false);
                        if (result != null) {
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                JsonArray features = result.getAsJsonArray("featured");
                                JsonArray commom = result.getAsJsonArray("common");
                                updateFeatureAdapter(features);
                                updateCommomAdapter(commom);
                            }
                        }
                    }
                });
    }

    private void updateCommomAdapter(JsonArray commom) {

        try {
            rowDatasCC.clear();
        } catch (Exception ex) {

        }
        for (int i = 0; i < commom.size(); i++) {
            jsonObject = commom.get(i).getAsJsonObject();
            rowDatasCC.add(new RowDataC(jsonObject.get("id").getAsString(), jsonObject.get("product_name").getAsString(), jsonObject.get("product_subname").getAsString(), jsonObject.get("image").getAsString(), jsonObject.get("unit_price").getAsString(), "4.0", jsonObject.get("url").getAsString()));
        }
        adapterCommom.notifyDataSetChanged();
    }

    private void updateFeatureAdapter(JsonArray features) {
        try {
            rowDataFs.clear();
        } catch (Exception ex) {

        }
        for (int i = 0; i < features.size(); i++) {
            jsonObject = features.get(i).getAsJsonObject();
            rowDataFs.add(new RowDataF(jsonObject.get("id").getAsString(), jsonObject.get("product_name").getAsString(), jsonObject.get("image").getAsString()));
        }
        adapterFeatured.notifyDataSetChanged();
    }

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void setupDropDown(View view) {
        String[] categoryList = {"All Category", "Category 1", "Category 2", "Category 3", "Category 4"};
        String[] sortbyList = {"Latest", "Alphabet", "App size", "Popularity", "Most Downloaded"};
        String[] filterPricListe = {"Free", "Paid", "All"};
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
            case R.id.rippleleft:
            case R.id.linearLeft:
                try {
                    showMessage("clicked");
                    recyclerView_Featured.getLayoutManager().scrollToPosition(layoutManagerFeatured.findFirstVisibleItemPosition() - 1);

                    // recyclerView_Featured.scrollToPosition(RecyclerViewAdapterF.position - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                break;
            case R.id.rippleright:
            case R.id.linearRight:
                try {
                    showMessage("clicked");
                    recyclerView_Featured.getLayoutManager().scrollToPosition(layoutManagerFeatured.findLastVisibleItemPosition() + 1);
                    //recyclerView_Featured.scrollToPosition(RecyclerViewAdapterF.position + 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }


}
