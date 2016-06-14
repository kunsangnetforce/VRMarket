package com.netforceinfotech.vrmarket.dashboard.app.commom;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class RowDataC {

    /*
    * "palyerId": 1,
      "playerName": "A",
      "playerPosition": "playerPosition"
    * */
    public String playerId, playerName, playerPosition, playerImage;


    public RowDataC(String playerId, String playerName, String playerPosition, String playerImage) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerImage = playerImage;
    }

}