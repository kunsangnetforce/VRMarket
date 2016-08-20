package com.netforceinfotech.vrmarket.search;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.general.GlobleVariable;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;
    private final String TAG = "SearchActivity";
    private MaterialSearchView searchView;
    ArrayList<String> appNames = new ArrayList<>();
    ArrayList<String> appIds = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private List<RowData> rowDatasCC = new ArrayList<>();
    private String imagePath;
    TextView textView;
    ImageView imageViewSearch, imageViewBack;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        imagePath = ((GlobleVariable) getApplication()).getImagePath();
        textView = (TextView) findViewById(R.id.textViewAppName);
        imageViewBack = (ImageView) findViewById(R.id.imageViewback);
        imageViewSearch = (ImageView) findViewById(R.id.imageViewSearch);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        editText = (EditText) findViewById(R.id.edittextSearch);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    if (v.length() > 2) {
                        getData(getApplicationContext(), v.getText().toString());
                    } else {
                        Toast.makeText(SearchActivity.this, "Input atleast 3 character", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    getData(getApplicationContext(), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageViewSearch.setImageResource(R.drawable.met_ic_close);
                return false;
            }
        });
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        setSearchView();
        setupToolBar();
        setupRecycle();

    }

    private void setupRecycle() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getApplicationContext(), rowDatasCC, imagePath);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String teams = "Search";
        getSupportActionBar().setTitle(teams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void setSearchView() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                getData(getApplicationContext(), query);
                searchView.setHint(query);
                View view = SearchActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if (newText.length() > 2) {
                    getData(getApplicationContext(), newText);
                }

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                rowDatasCC.clear();
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void getData(final Context context, String sstr) {

        String url = getResources().getString(R.string.surl);
        url = url + "?sstr=" + sstr;
        Log.i("result url", url);
        setHeader();
        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.i("result search", result.toString());
                            if (result.get("status").getAsString().equalsIgnoreCase("success")) {

                                JsonArray data = result.getAsJsonArray("data");
                                rowDatasCC.clear();
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                                if (data.size() <= 0) {
                                    textView.setVisibility(View.VISIBLE);
                                } else {
                                    textView.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < data.size(); i++) {

                                    JsonObject jsonObject = data.get(i).getAsJsonObject();
                                    String name = jsonObject.get("product_name").getAsString();
                                    String id = jsonObject.get("id").getAsString();
                                    String image = jsonObject.get("image").getAsString();
                                    String developer_name = jsonObject.get("developer_name").getAsString();
                                    String rating = jsonObject.get("rating").getAsString();
                                    rowDatasCC.add(new RowData(id, name, image, developer_name, rating));
                                }
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();


                            } else {
                                textView.setVisibility(View.VISIBLE);
                                rowDatasCC.clear();
                                adapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(context, "Something went wrong... try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return;
    }

    private void showMessage(String s) {
        Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
