package com.netforceinfotech.vrmarket.dashboard.games.featured;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataF {

    /*
    * "palyerId": 1,
      "app_name": "A",
      "developer_name": "developer_name"
    * */
    public String app_id, app_name, image_url;


    public RowDataF(String app_id, String app_name, String image_url) {
        this.app_id = app_id;
        this.app_name = app_name;
        this.image_url = image_url;
    }

}