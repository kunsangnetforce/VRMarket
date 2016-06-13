package com.netforceinfotech.vrmarket.dashboard.app;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netforceinfotech.vrmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    public static int position = 0;
    private static final String TAG = "tag_gcm";
    private List<RowData> itemList;
    private Context context;
    String teamName;
    private String url;
    private ProgressDialog pd;

    public RecyclerViewAdapter(Context context, List<RowData> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        this.position = position;
        holder.textView.setText("position" + position);
        Log.i("position", position + "*********");
    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {

        return 50;
        //  return itemList.size();
    }


}
