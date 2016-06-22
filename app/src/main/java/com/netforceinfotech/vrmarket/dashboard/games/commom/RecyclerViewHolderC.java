package com.netforceinfotech.vrmarket.dashboard.games.commom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolderC extends RecyclerView.ViewHolder {

    LinearLayout linearLayoutInfo,linearLayoutIcon;
    ImageView imageView;
    TextView textViewAppName,textViewDeveloperName,textViewCategory,textViewPrice,textViewRating;
    MaterialRippleLayout materialRippleLayoutDownload,materialRippleLayoutInfo;

    public RecyclerViewHolderC(View itemView) {
        super(itemView);
        //implementing onClickListener
        imageView= (ImageView) itemView.findViewById(R.id.imageView);
        linearLayoutIcon= (LinearLayout) itemView.findViewById(R.id.linearIcon);
        linearLayoutInfo= (LinearLayout) itemView.findViewById(R.id.linearInfo);
        materialRippleLayoutDownload= (MaterialRippleLayout) itemView.findViewById(R.id.rippleDownload);
        materialRippleLayoutInfo= (MaterialRippleLayout) itemView.findViewById(R.id.rippleInfo);
        textViewAppName= (TextView) itemView.findViewById(R.id.textViewAppName);
        textViewDeveloperName= (TextView) itemView.findViewById(R.id.textViewDeveloperName);
        textViewCategory= (TextView) itemView.findViewById(R.id.textViewCategory);
        textViewPrice= (TextView) itemView.findViewById(R.id.textViewPrice);
        textViewRating= (TextView) itemView.findViewById(R.id.textViewRating);

    }

}