package com.example.snakesandladders;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public class Dice {

    private static ArrayList<Image> diceImages = new ArrayList<>();

    protected static int getRandomValue(){
        return (int)(Math.random() * 6 + 1);
    }

    protected static void loadImages() throws IOException {
        diceImages.add(new Image(Dice.class.getResource("images/dice_0.png").openStream()));
        for(int i = 1; i <= 6; i++){
            diceImages.add(new Image(Dice.class.getResource("images/dice_"+ i + ".png").openStream()));
        }
    }

    public static ArrayList<Image> getDiceImages() {
        return diceImages;
    }
}
