package com.netforceinfotech.vrmarket.dashboard.app.featured;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolderF extends RecyclerView.ViewHolder {

    View view;
    TextView textView;
    MaterialRippleLayout materialRippleLayout;


    public RecyclerViewHolderF(View itemView) {
        super(itemView);
        //implementing onClickListener
        view = itemView;
        textView = (TextView) view.findViewById(R.id.textView);
        materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple);

    }

}