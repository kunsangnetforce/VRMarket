package com.netforceinfotech.vrmarket.dashboard.games;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.dashboard.games.commom.RecyclerViewAdapterC;
import com.netforceinfotech.vrmarket.dashboard.games.commom.RowDataC;
import com.netforceinfotech.vrmarket.dashboard.games.featured.RecyclerViewAdapterF;
import com.netforceinfotech.vrmarket.dashboard.games.featured.RowDataF;
import com.netforceinfotech.vrmarket.general.Category;
import com.netforceinfotech.vrmarket.general.GlobleVariable;
import com.netforceinfotech.vrmarket.general.MyCustomAdapter;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {
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
    private String type;
    int page = 1;
    ProgressBar progressBarFeature, progressBarCommom;
    TextView textViewNoFeature, textViewNoCommom;
    private String imagePath;
    boolean _areLecturesLoaded = false;
    private ArrayList<String> categoriesName, categoriesId, sortbyList, filterPricListe;
    private String selectedCategory = "", selectedPrice = "", selectedSortBy = "";
    boolean asc_sortby = true;
    private JsonArray staticapp;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded) {
            _areLecturesLoaded = true;
            getData(getActivity(), type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        type = "games";
        imagePath = ((GlobleVariable) getActivity().getApplication()).getImagePath();
        view = inflater.inflate(R.layout.fragment_game, container, false);
        textViewNoCommom = (TextView) view.findViewById(R.id.textViewNoCommomApp);
        textViewNoFeature = (TextView) view.findViewById(R.id.textViewNoFeaturedApp);
        progressBarCommom = (ProgressBar) view.findViewById(R.id.progressbar_commom);
        progressBarFeature = (ProgressBar) view.findViewById(R.id.progressbar_feature);
        rippleLeft = (MaterialRippleLayout) view.findViewById(R.id.rippleleft);
        rippleRight = (MaterialRippleLayout) view.findViewById(R.id.rippleright);
        linearLayoutLeft = (LinearLayout) view.findViewById(R.id.linearLeft);
        linearLayoutRight = (LinearLayout) view.findViewById(R.id.linearRight);
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.i("result page", page + "");
                getData(context, type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
            }
        });
       // getData(context, type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
        linearLayoutLeft.setOnClickListener(this);
        linearLayoutRight.setOnClickListener(this);
        rippleRight.setOnClickListener(this);
        rippleLeft.setOnClickListener(this);
        setupRecycleFeatured();
        setupRecycleCommom();
        setupDropDown(view);
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("result xxx", "clicked");
                selectedCategory = categoriesId.get(position);
                page = 1;
                getDataFilter(context, type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
                progressBarCommom.setVisibility(View.VISIBLE);
            }
        });
        free.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPrice = filterPricListe.get(position).toLowerCase();
                page = 1;
                getDataFilter(context, type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
                progressBarCommom.setVisibility(View.VISIBLE);
            }
        });
        latest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSortBy = sortbyList.get(position).toLowerCase();
                page = 1;
                getDataFilter(context, type, page + "", selectedCategory, selectedPrice, selectedSortBy, asc_sortby);
                asc_sortby = !asc_sortby;
                progressBarCommom.setVisibility(View.VISIBLE);
            }
        });
        return view;

    }

    private void getDataFilter(final Context context, String type, final String page, String selectedCategory, String selectedPrice, String selectedSortBy, boolean asc_sortby) {
        String order = "asc";
        if (asc_sortby) {
            order = "asc";
        } else {
            order = "desc";
        }
        String url = getResources().getString(R.string.url);
        url = url + "?page=" + page + "&type=" + type + "&category=" + selectedCategory + "&price=" + selectedPrice + "&col=" + selectedSortBy + "&order=" + order;
        Log.i("result url", url);
        setHeader();
        Log.i("result page1", page);
        Ion.with(context)
                .load(url)
                .setBodyParameter("type", type)
                .setBodyParameter("page", page)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        mSwipyRefreshLayout.setRefreshing(false);
                        progressBarFeature.setVisibility(View.GONE);
                        progressBarCommom.setVisibility(View.GONE);
                        if (result != null) {
                            Log.i("result app", result.toString());
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                showMessage("Load complete...");
                                GameFragment.this.page++;
                                JsonArray commom = result.getAsJsonArray("common");
                                if (commom.size() < 1) {
                                    textViewNoCommom.setVisibility(View.VISIBLE);
                                    recyclerView_Commom.setVisibility(View.GONE);
                                } else {
                                    textViewNoCommom.setVisibility(View.GONE);
                                    recyclerView_Commom.setVisibility(View.VISIBLE);
                                }
                                updateCommomAdapter(commom, page);
                            } else {
                                rowDatasCC.clear();
                                adapterCommom.notifyDataSetChanged();
                                adapterCommom.notifyDataSetChanged();
                                showMessage("No app found");
                                JsonArray jsonArray = new JsonArray();
                                updateCommomAdapter(jsonArray, page);
                            }
                        } else {
                            Log.e("result app", e.toString());
                        }
                    }
                });
    }


    private void getData(final Context context, String type, final String page, String selectedCategory, String selectedPrice, String selectedSortBy, boolean asc_sortby) {

        String order = "asc";
        if (asc_sortby) {
            order = "asc";
        } else {
            order = "desc";
        }
        String url = getResources().getString(R.string.url);
        url = url + "?page=" + page + "&type=" + type + "&category=" + selectedCategory + "&price=" + selectedPrice + "&col=" + selectedSortBy + "&order=" + order;
        Log.i("result url", url);
        setHeader();
        Log.i("result page1", page);
        Ion.with(context)
                .load(url)
                .setBodyParameter("type", type)
                .setBodyParameter("page", page)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        mSwipyRefreshLayout.setRefreshing(false);
                        progressBarFeature.setVisibility(View.GONE);
                        progressBarCommom.setVisibility(View.GONE);
                        if (result != null) {
                            Log.i("result app", result.toString());
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                showMessage("Load complete...");
                                GameFragment.this.page++;
                                if (page.equalsIgnoreCase("1")) {
                                    JsonArray features = result.getAsJsonArray("featured");
                                    if (features.size() < 1) {
                                        textViewNoFeature.setVisibility(View.VISIBLE);
                                        recyclerView_Featured.setVisibility(View.VISIBLE);
                                    } else {
                                        textViewNoFeature.setVisibility(View.GONE);
                                        recyclerView_Featured.setVisibility(View.VISIBLE);
                                    }
                                    staticapp = result.getAsJsonArray("static");
                                    updateCommomAdapter(staticapp, "1");
                                    updateFeatureAdapter(features);
                                }
                                JsonArray commom = result.getAsJsonArray("common");
                                if (commom.size() < 1) {
                                    textViewNoCommom.setVisibility(View.VISIBLE);
                                    recyclerView_Commom.setVisibility(View.GONE);
                                } else {
                                    textViewNoCommom.setVisibility(View.GONE);
                                    recyclerView_Commom.setVisibility(View.VISIBLE);
                                }
                                updateCommomAdapter(commom, page);
                            } else {
                                showMessage("No more app");

                            }
                        } else {
                            Log.e("result app", e.toString());
                        }
                    }
                });
    }

    private void setHeader() {
        final String appkey = getResources().getString(R.string.appkey);
        Ion.getDefault(context).getHttpClient().insertMiddleware(new AsyncHttpClientMiddleware() {
            @Override
            public void onRequest(OnRequestData data) {
                data.request.setHeader("APPKEY", appkey);
            }

            @Override
            public Cancellable getSocket(GetSocketData data) {
                return null;
            }

            @Override
            public boolean exchangeHeaders(OnExchangeHeaderData data) {
                return false;
            }

            @Override
            public void onRequestSent(OnRequestSentData data) {

            }

            @Override
            public void onHeadersReceived(OnHeadersReceivedDataOnRequestSentData data) {

            }

            @Override
            public void onBodyDecoder(OnBodyDataOnRequestSentData data) {

            }

            @Override
            public void onResponseComplete(OnResponseCompleteDataOnRequestSentData data) {

            }
        });
    }

    private void updateCommomAdapter(JsonArray commom, String page) {

        if (page.equalsIgnoreCase("1")) {
            try {
                rowDatasCC.clear();
                for (int i = 0; i < staticapp.size(); i++) {
                    jsonObject = staticapp.get(i).getAsJsonObject();
                    rowDatasCC.add(new RowDataC(jsonObject.get("id").getAsString(), jsonObject.get("product_name").getAsString(), jsonObject.get("developer_name").getAsString(), jsonObject.get("image").getAsString(), jsonObject.get("sales_price").getAsString(), "4.0", jsonObject.get("url").getAsString(), jsonObject.get("title").getAsString(),true));
                }
            } catch (Exception ex) {

            }
        }
        for (int i = 0; i < commom.size(); i++) {
            jsonObject = commom.get(i).getAsJsonObject();
            rowDatasCC.add(new RowDataC(jsonObject.get("id").getAsString(), jsonObject.get("product_name").getAsString(), jsonObject.get("developer_name").getAsString(), jsonObject.get("image").getAsString(), jsonObject.get("sales_price").getAsString(), jsonObject.get("rating").getAsString(), jsonObject.get("url").getAsString(), jsonObject.get("title").getAsString(),false));
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
        ArrayList<Category> categories = ((GlobleVariable) getActivity().getApplication()).getCategories();
        categoriesName = new ArrayList<>();
        categoriesId = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            categoriesId.add(categories.get(i).id);
            categoriesName.add(categories.get(i).category);
        }
        sortbyList = new ArrayList<>();
        sortbyList.add("Latest");
        sortbyList.add("Alphabet");

        filterPricListe = new ArrayList<>();
        filterPricListe.add("Free");
        filterPricListe.add("Paid");
        filterPricListe.add("All");

        MyCustomAdapter adapter1 = new MyCustomAdapter(getActivity(), R.layout.spinner_text_layout, categoriesName);
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
        adapterFeatured = new RecyclerViewAdapterF(context, rowDataFs, imagePath);
        recyclerView_Featured.setAdapter(adapterFeatured);
    }

    private void setupRecycleCommom() {

        recyclerView_Commom = (RecyclerView) view.findViewById(R.id.recyclerCommom);
        layoutManagerCommom = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView_Commom.setLayoutManager(layoutManagerCommom);
        adapterCommom = new RecyclerViewAdapterC(context, rowDatasCC, imagePath);
        recyclerView_Commom.setAdapter(adapterCommom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLeft:
                try {
                    recyclerView_Featured.smoothScrollToPosition(layoutManagerFeatured.findFirstVisibleItemPosition() - 1);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case R.id.linearRight:
                try {
                    recyclerView_Featured.smoothScrollToPosition(layoutManagerFeatured.findLastVisibleItemPosition() +1);

                    //recyclerView_Featured.scrollToPosition(RecyclerViewAdapter.position + 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

}
