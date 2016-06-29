package com.netforceinfotech.vrmarket.general;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.netforceinfotech.vrmarket.R;

import java.util.ArrayList;

/**
 * Created by User1 on 04-01-2016.
 */
public class MyCustomAdapter extends ArrayAdapter<String> {

    ArrayList<String> items;
    int textViewResourceId;
    Context context;

    public MyCustomAdapter(Context context, int textViewResourceId,
                           ArrayList<String> items) {
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
        View view = getCustomView(position, convertView, parent);
        return view;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(textViewResourceId, parent, false);
        TextView label = (TextView) row.findViewById(R.id.spinnerTarget);
        label.setText(items.get(position));
        return row;
    }
}