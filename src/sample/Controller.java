package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private ImageView dice;

    @FXML
    void exitClicked(ActionEvent event) {

        //TODO POP UP TO ASK "Do you really want to EXIT?"

        Platform.exit();
        System.exit(0);
    }

    @FXML
    void rollClicked(ActionEvent event) {
        Dice.loadImages();
        int a = Dice.getRandomValue();
        switch (a){
            case 1: dice.setImage(Dice.getDiceImages().get(1));
            break;
            case 2: dice.setImage(Dice.getDiceImages().get(2));
                break;
            case 3: dice.setImage(Dice.getDiceImages().get(3));
                break;
            case 4: dice.setImage(Dice.getDiceImages().get(4));
                break;
            case 5: dice.setImage(Dice.getDiceImages().get(5));
                break;
            case 6: dice.setImage(Dice.getDiceImages().get(6));
                break;
            default:
                dice.setImage(Dice.getDiceImages().get(0));
        }

    }

}

