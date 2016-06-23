package com.netforceinfotech.vrmarket.app_detail.samecategory;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class RecyclerViewAdapterS extends RecyclerView.Adapter<RecyclerViewHolderS> {

    public static int position = 0;
    private static final String TAG = "tag_gcm";
    private List<RowDataS> itemList;
    private Context context;
    String teamName;
    private String url;
    private ProgressDialog pd;
    String imagePath;

    public RecyclerViewAdapterS(Context context, List<RowDataS> itemList, String imagePath) {
        this.itemList = itemList;
        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    public RecyclerViewHolderS onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_s, parent, false);
        RecyclerViewHolderS viewHolder = new RecyclerViewHolderS(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderS holder, final int position) {
        this.position = position;
        String imagePathDetail = imagePath + itemList.get(position).image_url;

        holder.textView.setText(itemList.get(position).app_name);
        Picasso.with(context)
                .load(imagePathDetail.replace(" ", "%20"))
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.imageView);
        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", itemList.get(position).app_id);
                bundle.putString("app_name",itemList.get(position).app_name);
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
