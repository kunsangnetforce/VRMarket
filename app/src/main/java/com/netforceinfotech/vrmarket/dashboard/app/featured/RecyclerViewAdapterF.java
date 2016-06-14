package com.netforceinfotech.vrmarket.dashboard.app.featured;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.netforceinfotech.vrmarket.R;

import java.util.List;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewAdapterF extends RecyclerView.Adapter<RecyclerViewHolderF> {

    public static int position = 0;
    private static final String TAG = "tag_gcm";
    private List<RowDataF> itemList;
    private Context context;
    String teamName;
    private String url;
    private ProgressDialog pd;

    public RecyclerViewAdapterF(Context context, List<RowDataF> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public RecyclerViewHolderF onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_f, parent, false);
        RecyclerViewHolderF viewHolder = new RecyclerViewHolderF(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderF holder, final int position) {
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
