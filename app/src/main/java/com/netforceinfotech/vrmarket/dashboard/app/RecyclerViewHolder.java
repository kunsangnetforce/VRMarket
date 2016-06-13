package com.netforceinfotech.vrmarket.dashboard.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView textView;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        //implementing onClickListener
        view = itemView;
        textView= (TextView) view.findViewById(R.id.textView);

    }

}