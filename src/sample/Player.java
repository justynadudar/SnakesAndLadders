package sample;

import javafx.scene.shape.Circle;

public class Player extends Circle {
    private int playerPosition;
    private boolean playerTurn;
    private static double playerXPosition;
    private static double playerYPosition;

    public Player(double radius, String id){
        this.playerXPosition = 22.5;
        this.playerYPosition = 427.5;
        this.playerPosition = 1;
        this.playerTurn = false;
        this.getStyleClass().add("style.css");
        this.setRadius(radius);
        this.setId(id);
        this.setTranslateX(this.playerXPosition);
        this.setTranslateY(this.playerYPosition);
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void changePlayerTurn() {
        if(this.playerTurn == true)
            this.playerTurn = false;
        else
            this.playerTurn = true;
    }
}
