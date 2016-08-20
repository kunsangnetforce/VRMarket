package com.netforceinfotech.vrmarket.search;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.netforceinfotech.vrmarket.R;
import com.netforceinfotech.vrmarket.app_detail.AppDetailActivity;
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
    String imagePath;

    public RecyclerViewAdapter(Context context, List<RowData> itemList, String imagePath) {
        this.itemList = itemList;
        this.context = context;
        this.imagePath = imagePath;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_search, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        this.position = position;
        String imagePathDetail = imagePath + itemList.get(position).image_url;
        holder.textViewAppName.setText(itemList.get(position).app_name);
        Picasso.with(context)
                .load(imagePathDetail.replace(" ", "%20"))
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.imageView);
        holder.textViewDeveloperName.setText(itemList.get(position).developerName);
        holder.ratingBar.setRating(Float.parseFloat(itemList.get(position).rating));
        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("id", itemList.get(position).app_id);
                bundle.putString("app_name", itemList.get(position).app_name);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {

        return itemList.size();
        //  return itemList.size();
    }


}
