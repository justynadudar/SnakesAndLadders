package com.example.snakesandladders;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Player extends Circle {
    private Tile tileThePlayerIsOn;
    private ImageView playerImage;

    public Player(ImageView image){
        this.playerImage = image;
    }

    public void setTileThePlayerIsOn(Tile tileThePlayerIsOn) {
        this.tileThePlayerIsOn = tileThePlayerIsOn;
    }

    public Tile getTileThePlayerIsOn() {
        return tileThePlayerIsOn;
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }

}
