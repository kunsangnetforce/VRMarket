package com.netforceinfotech.vrmarket.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView textViewAppName, textViewDeveloperName;
    ImageView imageView;
    MaterialRippleLayout materialRippleLayout;
    RatingBar ratingBar;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        //implementing onClickListener
        view = itemView;
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textViewAppName = (TextView) view.findViewById(R.id.textViewAppName);
        textViewDeveloperName = (TextView) view.findViewById(R.id.textViewDeveloperName);
        materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple);
        ratingBar= (RatingBar) view.findViewById(R.id.ratingBar);

    }

}