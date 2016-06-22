package com.netforceinfotech.vrmarket.dashboard.games.commom;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataC {

    /*
    * "palyerId": 1,
      "app_name": "A",
      "developer_name": "developer_name"
    * */
    public String app_id, app_name, developer_name, image_url, price, rating, app_url,category;


    public RowDataC(String app_id, String app_name, String developer_name, String image_url, String price, String rating, String app_url,String category) {
        this.app_id = app_id;
        this.app_name = app_name;
        this.developer_name = developer_name;
        this.image_url = image_url;
        this.price = price;
        this.rating = rating;
        this.app_url = app_url;
        this.category=category;
    }

}