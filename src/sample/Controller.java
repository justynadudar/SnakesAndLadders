package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRoll;

    @FXML
    private ImageView dice;

    @FXML
    private ImageView playerBlueImage;

    ArrayList<Tile> board = new ArrayList<>();

//    @FXML
//    private ImageView playerYellowImage;

    Player playerBlue;
//    Player playerYellow = new Player(playerYellowImage);

    void changeDiceImage(int diceValue){
        dice.setImage(Dice.getDiceImages().get(diceValue));
    }

    void changeTile(int diceValue, Player player){
        int previousTileIndex = board.indexOf(player.getTileThePlayerIsOn());
        player.setTileThePlayerIsOn(board.get(previousTileIndex+diceValue));
        player.getPlayerImage().setLayoutX(player.getTileThePlayerIsOn().getX());
        player.getPlayerImage().setLayoutY(player.getTileThePlayerIsOn().getY());
    }

    @FXML
    void playClicked(ActionEvent event) {
        btnPlay.setVisible(false);
        btnRoll.setVisible(true);
        btnExit.setVisible(true);

        //TODO check which player's turn
        playerBlue = new Player(playerBlueImage);
        playerBlue.getPlayerImage().setVisible(true);
        dice.setImage(Dice.getDiceImages().get(0));

        int x = 0;
        int y = 360;
        int operation = 1;

        for(int i = 1; i <= 100; i++){
            board.add(new Tile(i, x*40 , y));
            if((i) %10 == 0 && (i) %20 == 0){
                x = 0;
                y -= 40;
                operation = 1;
            } else if((i) %20 == 10 && (i) %10 == 0){
                x = 9;
                y -= 40;
                operation = -1;
            }
            else x += operation;
        }
        playerBlue.setTileThePlayerIsOn(board.get(0));

        playerBlue.getPlayerImage().setLayoutX(board.get(0).getX());
        playerBlue.getPlayerImage().setLayoutY(board.get(0).getY());
    }

    @FXML
    void exitClicked(ActionEvent event) {

        //TODO POP UP TO ASK "Do you really want to EXIT?"

        Platform.exit();
        System.exit(0);
    }

    @FXML
    void rollClicked(ActionEvent event) {

        int a = Dice.getRandomValue();
        switch (a){
            case 1:
                changeDiceImage(1);
                changeTile(1, playerBlue);
                break;
            case 2:
                changeDiceImage(2);
                changeTile(2, playerBlue);
                break;
            case 3:
                changeDiceImage(3);
                changeTile(3, playerBlue);
                break;
            case 4:
                changeDiceImage(4);
                changeTile(4, playerBlue);
                break;
            case 5:
                changeDiceImage(5);
                changeTile(5, playerBlue);
                break;
            case 6:
                changeDiceImage(6);
                changeTile(6, playerBlue);
                break;
            default:
                changeDiceImage(0);
        }

    }

}

