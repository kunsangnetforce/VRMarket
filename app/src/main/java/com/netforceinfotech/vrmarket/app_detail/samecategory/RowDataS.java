package com.netforceinfotech.vrmarket.app_detail.samecategory;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataS {

    /*
    * "palyerId": 1,
      "app_name": "A",
      "developer_name": "developer_name"
    * */
    public String app_id, app_name, image_url;


    public RowDataS(String app_id, String app_name, String image_url) {
        this.app_id = app_id;
        this.app_name = app_name;
        this.image_url = image_url;
    }

}