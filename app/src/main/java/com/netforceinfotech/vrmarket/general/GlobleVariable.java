package com.netforceinfotech.vrmarket.general;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Netforce on 6/21/2016.
 */public class GlobleVariable extends Application {

    private String imagePath;
    private ArrayList<Category> category;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String someVariable) {
        this.imagePath = someVariable;
    }

    public ArrayList<Category> getCategories() {
        return category;
    }

    public void setCategory(ArrayList<Category> someVariable) {
        this.category = someVariable;
    }
}