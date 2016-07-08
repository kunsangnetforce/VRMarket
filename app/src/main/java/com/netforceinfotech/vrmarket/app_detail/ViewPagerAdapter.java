package com.netforceinfotech.vrmarket.app_detail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netforceinfotech.vrmarket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 10/6/2015.
 */
public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> imageList;
    String imagePath;

    public ViewPagerAdapter(Context context, ArrayList<String> imageList, String imagePath) {
        this.context = context;
        this.imageList = imageList;
        this.imagePath = imagePath;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return (view == ((LinearLayout) o));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_inflate, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.img_pager);
        Picasso.with(context)
                .load(imagePath + imageList.get(position).replace(" ", "%20"))
                .placeholder(R.color.md_blue_grey_50)
                .error(R.color.md_blue_grey_50)
                .into(image);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
        //super.destroyItem(container, position, object);
    }
}
