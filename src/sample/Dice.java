package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Set;

public class Dice {

    private static ArrayList<Image> diceImages = new ArrayList<>();

    protected static int getRandomValue(){
        return (int)(Math.random() * 6 + 1);
    }

    protected static void loadImages(){
        diceImages.add(new Image("/resources/dice_0.png"));
        for(int i = 1; i <= 6; i++){
            diceImages.add(new Image("/resources/dice_"+ i + ".png"));
        }
    }

    public static ArrayList<Image> getDiceImages() {
        return diceImages;
    }
}
