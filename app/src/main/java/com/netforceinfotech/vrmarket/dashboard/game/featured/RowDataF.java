package com.netforceinfotech.vrmarket.dashboard.game.featured;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataF {

    /*
    * "palyerId": 1,
      "app_name": "A",
      "developer_name": "developer_name"
    * */
    public String playerId, playerName, playerPosition, playerImage;


    public RowDataF(String playerId, String playerName, String playerPosition, String playerImage) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerImage = playerImage;
    }

}