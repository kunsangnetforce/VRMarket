package com.netforceinfotech.vrmarket.general;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.ion.Ion;
import com.netforceinfotech.vrmarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    Context context;
    TextView textViewEmail, textViewPhone, textViewAddress, textViewWebsite, textViewFax;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        context = getActivity();
        textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        textViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
        textViewWebsite = (TextView) view.findViewById(R.id.textViewWebsite);
        textViewFax = (TextView) view.findViewById(R.id.textViewFax);
        getContact();
        return view;
    }

    private void getContact() {
        setHeader();
        String url = getResources().getString(R.string.purl);
        Ion.with(context)
                .load(url + "?id=2")
                .setBodyParameter("id", "2")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.i("result appdetail", result.toString());
                            String status = result.get("status").getAsString();
                            if (status.equalsIgnoreCase("success")) {
                                JsonObject data = result.get("data").getAsJsonObject();
                                String address = data.get("address").getAsString();
                                String fax = data.get("fax").getAsString();
                                String url = data.get("url").getAsString();
                                String phone = data.get("phone").getAsString();
                                String email = data.get("email").getAsString();
                                textViewWebsite.setText(url);
                                textViewPhone.setText(phone);
                                textViewEmail.setText(email);
                                textViewFax.setText(fax);
                                textViewAddress.setText(address);
                            } else {
                                Toast.makeText(context, "Something went wrong... try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(context, "Something went wrong... try again", Toast.LENGTH_SHORT).show();
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
}
