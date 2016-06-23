package com.netforceinfotech.vrmarket.app_detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RecyclerViewAdapterS;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RowDataS;
import com.netforceinfotech.vrmarket.dashboard.general.GlobleVariable;
import com.netforceinfotech.vrmarket.dashboard.general.NoInternet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AppDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView_Same;
    private Context context;
    private LinearLayoutManager layoutManagerSame;
    private RecyclerViewAdapterS adapterSame;
    private ArrayList<RowDataS> rowDataS = new ArrayList<>();
    private Toolbar toolbar;
    private String url;
    private SliderLayout mDemoSlider;
    private String imagePath;
    ImageView imageView, imageViewDownload;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    TextView textViewAppName, textViewDeveloperName, textViewCategory, textViewPrice, textViewRating, textViewDescription, textViewSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        context = this;
        imagePath = ((GlobleVariable) getApplication()).getImagePath();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String app_name = bundle.getString("app_name");
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        if (!isNetworkAvailable()) {
            Intent intent = new Intent(getApplicationContext(), NoInternet.class);
            startActivity(intent);
            finish();
        }
        getAppDetail(id);
        setupToolBar(app_name);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewDownload = (ImageView) findViewById(R.id.imageViewDownload);
        imageViewDownload.setOnClickListener(this);
        textViewAppName = (TextView) findViewById(R.id.textViewAppName);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewDeveloperName = (TextView) findViewById(R.id.textViewDeveloperName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewSimilar = (TextView) findViewById(R.id.textViewSimiar);
        setupRecycleFeatured();
    }

    private void getAppDetail(String id) {
        setHeader();
        String url = getResources().getString(R.string.url);
        Ion.with(context)
                .load(url + "?id=" + id)
                .setBodyParameter("id", id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.i("result appdetail", result.toString());
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                setAppDetail(result);
                            }
                        }
                    }
                });
    }

    private void setHeader() {
        final String appkey = getResources().getString(R.string.appkey);
        Ion.getDefault(getApplicationContext()).getHttpClient().insertMiddleware(new AsyncHttpClientMiddleware() {
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

    private void setAppDetail(JsonObject result) {
        JsonObject data = result.get("data").getAsJsonObject();
        String image = data.get("image").getAsString();
        String appName = data.get("product_name").getAsString();
        String developerName = data.get("developer_name").getAsString();
        String category = data.get("title").getAsString();
        String price = data.get("sales_price").getAsString();
        String rating = data.get("rating").getAsString();
        url = data.get("url").getAsString();
        String description = data.get("short_description").getAsString();
        JsonArray images = result.get("images").getAsJsonArray();
        Log.i("result images", "" + images.size());
        JsonArray similar = result.get("similar").getAsJsonArray();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            JsonObject jsonObject = images.get(i).getAsJsonObject();
            String imagePath = jsonObject.get("image").getAsString();
            Log.i("result imagepath", imagePath);
            arrayList.add(imagePath);
        }
        setupSimilar(similar);
        Log.i("result arraylist", "" + arrayList.size());
        setupSlider(arrayList);
        String imagePathDetail = imagePath + image;
        Picasso.with(context)
                .load(imagePathDetail.replace(" ", "%20"))
                .placeholder(R.color.md_blue_grey_50)
                .error(R.color.md_blue_grey_50)
                .into(imageView);
        textViewAppName.setText(appName);
        textViewDeveloperName.setText(developerName);
        textViewCategory.setText(category);
        textViewPrice.setText("Price:$" + price);
        textViewRating.setText("Rating:" + rating);
        textViewDescription.setText(description);
        textViewSimilar.setText("More in '" + category + "'");
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    private void setupSimilar(JsonArray similar) {
        for (int i = 0; i < similar.size(); i++) {
            JsonObject jsonObject = similar.get(i).getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String image = jsonObject.get("image").getAsString();
            String appname = jsonObject.get("product_name").getAsString();
            rowDataS.add(new RowDataS(id, appname, image));
        }
        adapterSame.notifyDataSetChanged();
    }

    private void setupSlider(ArrayList<String> arrayList) {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < arrayList.size(); i++) {
            Log.i("result iterate", "" + i);
            url_maps.put("" + i, imagePath + arrayList.get(i));
        }

        for (String name : url_maps.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
            ;

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
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
        adapterSame = new RecyclerViewAdapterS(context, rowDataS, imagePath);
        recyclerView_Same.setAdapter(adapterSame);
    }

    private void setupToolBar(String app_name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name);
        ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewDownload:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
