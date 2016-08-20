package com.netforceinfotech.vrmarket.app_detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.SliderLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RecyclerViewAdapterS;
import com.netforceinfotech.vrmarket.app_detail.samecategory.RowDataS;
import com.netforceinfotech.vrmarket.general.GlobleVariable;
import com.netforceinfotech.vrmarket.general.NoInternet;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

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
    TextView textViewAppName, textViewDeveloperName, textViewCategory, textViewPrice, textViewRating, textViewDescription, textViewSimilar, textViewShowmore, textViewNoSimilarApp;
    private String description = "";
    private String app_name;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private final Handler handler = new Handler();
    public int value = 0;
    ExpandableTextView expandableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        context = this;
        imagePath = ((GlobleVariable) getApplication()).getImagePath();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        Log.i("vr_market_id", id);
        app_name = bundle.getString("app_name");
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        expandableTextView = (ExpandableTextView) findViewById(R.id.expandableTextView);
        // mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        if (!isNetworkAvailable()) {
            Intent intent = new Intent(getApplicationContext(), NoInternet.class);
            startActivity(intent);
            finish();
        }
        viewPager = (ViewPager) findViewById(R.id.vie_pager);

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
        textViewShowmore = (TextView) findViewById(R.id.textViewShowmore);
        textViewDescription.setOnClickListener(this);
        textViewNoSimilarApp = (TextView) findViewById(R.id.textViewNoSimmilarApp);
        textViewShowmore.setOnClickListener(this);
        setupRecycleFeatured();
        getAppDetail(id);

    }

    private void getAppDetail(String id) {
        setHeader();
        String url = getResources().getString(R.string.url);
        Log.i("vr_market_url", url + "?id=" + id);
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
                            } else if (status.equalsIgnoreCase("failed")) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AppDetailActivity.this, "Error getting data. Please try again", Toast.LENGTH_SHORT).show();
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
        String description=data.get("short_description").getAsString();
        Log.i("kunsang_desc",description);
        String developerName = data.get("developer_name").getAsString();
        String category = data.get("title").getAsString();
        String pricestring = data.get("sales_price").getAsString();
        String rating = data.get("rating").getAsString();
        url = data.get("url").getAsString();
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
        float price = Float.parseFloat(pricestring);
        if (price == 0) {
            textViewPrice.setText("Price: Free");
        } else {
            textViewPrice.setText("Price: $ " + pricestring);
        }
        textViewRating.setText("Rating: " + rating);
        textViewDescription.setText(description);
        expandableTextView.setText(description);
        textViewSimilar.setText("More in '" + category + "'");
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Layout l = expandableTextView.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    textViewShowmore.setVisibility(View.VISIBLE);
                } else {
                    textViewShowmore.setVisibility(View.GONE);
                }
            //Log.d(TAG, "Text is ellipsized");
        }

    }

    private void setupSimilar(JsonArray similar) {
        if (similar.size() <= 0) {
            textViewNoSimilarApp.setVisibility(View.VISIBLE);
        } else {
            textViewNoSimilarApp.setVisibility(View.GONE);
            for (int i = 0; i < similar.size(); i++) {
                JsonObject jsonObject = similar.get(i).getAsJsonObject();
                String id = jsonObject.get("id").getAsString();
                String image = jsonObject.get("image").getAsString();
                String appname = jsonObject.get("product_name").getAsString();
                rowDataS.add(new RowDataS(id, appname, image));
            }
        }
        adapterSame.notifyDataSetChanged();
    }

    private void setupSlider(final ArrayList<String> arrayList) {
       /* HashMap<String, String> url_maps = new HashMap<String, String>();
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
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
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
        mDemoSlider.setDuration(4000);*/
        adapter = new ViewPagerAdapter(AppDetailActivity.this, arrayList, imagePath);

        viewPager.setAdapter(adapter);
        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(viewPager);

        final Runnable r = new Runnable() {
            public void run() {

                viewPager.setCurrentItem(value, true);
                handler.postDelayed(this, 3500);

                if (value == arrayList.size()) {
                    value = -1;
                }
                value++;
            }
        };
        handler.postDelayed(r, 3500);

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
            case R.id.textViewDescription:
                showPopup(description);
                break;
            case R.id.textViewShowmore:
                if (expandableTextView.isExpanded())
                {
                    expandableTextView.collapse();
                    textViewShowmore.setText(R.string.show_more);
                }
                else
                {
                    expandableTextView.expand();
                    textViewShowmore.setText(R.string.show_less);
                }
        }
    }

    private void showPopup(String description) {
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(app_name)
                .customView(R.layout.description, wrapInScrollView)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })

                .show();
        TextView textView = (TextView) dialog.findViewById(R.id.textViewAppName);
        textView.setText(description);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
