package com.netforceinfotech.vrmarket.dashboard.general;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.netforceinfotech.vrmarket.R;

/**
 * Created by User1 on 04-01-2016.
 */ public class MyCustomAdapter extends ArrayAdapter<String> {

    String[] items;
    int textViewResourceId;
    Context context;

    public MyCustomAdapter(Context context, int textViewResourceId,
                           String[] items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.textViewResourceId = textViewResourceId;
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(textViewResourceId, parent, false);
        TextView label = (TextView) row.findViewById(R.id.spinnerTarget);
        label.setText(items[position]);
        return row;
    }
}