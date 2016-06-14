package com.netforceinfotech.vrmarket.dashboard.game.commom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolderC extends RecyclerView.ViewHolder {

    LinearLayout linearLayoutInfo,linearLayoutIcon;
    ImageView imageViewDownload;
    MaterialRippleLayout materialRippleLayoutDownload,materialRippleLayoutInfo;


    public RecyclerViewHolderC(View itemView) {
        super(itemView);
        //implementing onClickListener
        imageViewDownload= (ImageView) itemView.findViewById(R.id.imageViewDownload);
        linearLayoutIcon= (LinearLayout) itemView.findViewById(R.id.linearIcon);
        linearLayoutInfo= (LinearLayout) itemView.findViewById(R.id.linearInfo);
        materialRippleLayoutDownload= (MaterialRippleLayout) itemView.findViewById(R.id.rippleDownload);
        materialRippleLayoutInfo= (MaterialRippleLayout) itemView.findViewById(R.id.rippleInfo);

    }

}