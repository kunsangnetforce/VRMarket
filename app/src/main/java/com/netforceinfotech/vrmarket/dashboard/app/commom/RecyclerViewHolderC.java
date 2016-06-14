package com.netforceinfotech.vrmarket.dashboard.app.commom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.netforceinfotech.vrmarket.R;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RecyclerViewHolderC extends RecyclerView.ViewHolder {

    View view;
    TextView textView;


    public RecyclerViewHolderC(View itemView) {
        super(itemView);
        //implementing onClickListener
        view = itemView;
        textView= (TextView) view.findViewById(R.id.textView);

    }

}