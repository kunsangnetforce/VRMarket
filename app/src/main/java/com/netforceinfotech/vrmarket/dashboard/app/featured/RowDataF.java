package com.netforceinfotech.vrmarket.dashboard.app.featured;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataF {

    /*
    * "palyerId": 1,
      "playerName": "A",
      "playerPosition": "playerPosition"
    * */
    public String playerId, playerName, playerPosition, playerImage;


    public RowDataF(String playerId, String playerName, String playerPosition, String playerImage) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerImage = playerImage;
    }

}