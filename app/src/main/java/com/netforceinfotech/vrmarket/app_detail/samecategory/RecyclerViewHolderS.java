package com.netforceinfotech.vrmarket.app_detail.samecategory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolderS extends RecyclerView.ViewHolder {

    MaterialRippleLayout materialRippleLayout;
    View view;
    TextView textView;


    public RecyclerViewHolderS(View itemView) {
        super(itemView);
        //implementing onClickListener
        view = itemView;
        textView= (TextView) view.findViewById(R.id.textView);
        materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple);

    }

}