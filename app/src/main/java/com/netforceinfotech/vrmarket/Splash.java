package com.netforceinfotech.vrmarket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.dashboard.Dashboard;
import com.netforceinfotech.vrmarket.general.Category;
import com.netforceinfotech.vrmarket.general.GlobleVariable;
import com.netforceinfotech.vrmarket.general.NoInternet;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    ArrayList<Category> arrayList = new ArrayList<>();
    private JsonObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getGeneralData();
    }


    private void getGeneralData() {
        setHeader();
        String url = getResources().getString(R.string.curl);
        Ion.with(getApplicationContext())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.i("result category", result.toString());
                            if (result.get("status").getAsString().equalsIgnoreCase("success")) {
                                String imagePath = result.get("imgpath").getAsString();
                                ((GlobleVariable) Splash.this.getApplication()).setImagePath(imagePath);
                            }
                            JsonArray jsonArray = result.get("data").getAsJsonArray();
                            for (int i = 0; i < jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                arrayList.add(new Category(jsonObject.get("id").getAsString(), jsonObject.get("title").getAsString()));
                            }
                            arrayList.add(new Category("", "All Category"));
                            ((GlobleVariable) Splash.this.getApplication()).setCategory(arrayList);
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), NoInternet.class);
                            startActivity(intent);
                            finish();
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
