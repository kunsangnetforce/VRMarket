package com.netforceinfotech.vrmarket.dashboard.app.commom;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
public class RecyclerViewAdapterC extends RecyclerView.Adapter<RecyclerViewHolderC> {

    public static int position = 0;
    private List<RowDataC> itemList;
    private Context context;
    private String imagePath;


    public RecyclerViewAdapterC(Context context, List<RowDataC> itemList, String imagePath) {
        this.itemList = itemList;
        this.context = context;
        this.imagePath = imagePath;

    }

    @Override
    public RecyclerViewHolderC onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_c, parent, false);
        RecyclerViewHolderC viewHolder = new RecyclerViewHolderC(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderC holder, final int position) {
        String imagePathDetail = imagePath + itemList.get(position).image_url;
        holder.materialRippleLayoutDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(position).app_url)));
            }
        });
        holder.materialRippleLayoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", itemList.get(position).app_id);
                bundle.putString("app_name", itemList.get(position).app_name);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        Picasso.with(context)
                .load(imagePathDetail.replace(" ", "%20"))
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.imageView);
        holder.textViewAppName.setText(itemList.get(position).app_name);
        holder.textViewDeveloperName.setText(itemList.get(position).developer_name);
        holder.textViewPrice.setText("Price: $ " + itemList.get(position).price);
        holder.textViewRating.setText("Rating:" + itemList.get(position).rating);
        holder.textViewCategory.setText(itemList.get(position).category);
        if (itemList.get(position).staticApp) {
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        else {
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

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
