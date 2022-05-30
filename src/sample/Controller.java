package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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

    @FXML
    private Text textDiceValue;

    @FXML
    private Text textBeforeValue;

    ArrayList<Tile> board = new ArrayList<>();

//    @FXML
//    private ImageView playerYellowImage;

    Player playerBlue;
//    Player playerYellow = new Player(playerYellowImage);

    void changeDiceImageAndText(int value){
        textDiceValue.setText(String.valueOf(value));
        dice.setImage(Dice.getDiceImages().get(value));
        textDiceValue.setText(String.valueOf(value));
    }

    void changeTile(int diceValue, Player player){
        int previousTileIndex = board.indexOf(player.getTileThePlayerIsOn());

        if(previousTileIndex+diceValue > 99) player.setTileThePlayerIsOn(board.get(previousTileIndex));
        else player.setTileThePlayerIsOn(board.get(previousTileIndex+diceValue));

        if(player.getTileThePlayerIsOn().getSnake() != null){
            player.setTileThePlayerIsOn(player.getTileThePlayerIsOn().getSnake().endTile);
        }
        else if(player.getTileThePlayerIsOn().getLadder() != null){
            player.setTileThePlayerIsOn(player.getTileThePlayerIsOn().getLadder().endTile);
        }

        player.getPlayerImage().setLayoutX(player.getTileThePlayerIsOn().getX());
        player.getPlayerImage().setLayoutY(player.getTileThePlayerIsOn().getY());

        if(player.getTileThePlayerIsOn().getId() == 100){
            btnPlay.setVisible(true);
            btnRoll.setVisible(false);
            btnExit.setVisible(false);
            dice.setVisible(false);
            textDiceValue.setVisible(false);
            textBeforeValue.setText("    You win!");

        }
    }

    @FXML
    void playClicked(ActionEvent event) {
        btnPlay.setVisible(false);
        btnRoll.setVisible(true);
        btnExit.setVisible(true);
        textBeforeValue.setVisible(true);
        textBeforeValue.setText("  Your move!");

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

        Snake snake1 = new Snake(board.get(58),board.get(19));
        board.get(58).setSnake(snake1);
        Snake snake2 = new Snake(board.get(94),board.get(66));
        board.get(94).setSnake(snake2);
        Ladder ladder1 = new Ladder(board.get(6),board.get(28));
        board.get(6).setLadder(ladder1);
        Ladder ladder2 = new Ladder(board.get(35),board.get(43));
        board.get(35).setLadder(ladder2);
        Ladder ladder3 = new Ladder(board.get(47),board.get(51));
        board.get(47).setLadder(ladder3);
        Ladder ladder4 = new Ladder(board.get(63),board.get(81));
        board.get(63).setLadder(ladder4);


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
        textDiceValue.setVisible(true);
        textBeforeValue.setText("You rolled:");
        int a = Dice.getRandomValue();
        switch (a){
            case 1:
                changeDiceImageAndText(1);
                changeTile(1, playerBlue);
                break;
            case 2:
                changeDiceImageAndText(2);
                changeTile(2, playerBlue);
                break;
            case 3:
                changeDiceImageAndText(3);
                changeTile(3, playerBlue);
                break;
            case 4:
                changeDiceImageAndText(4);
                changeTile(4, playerBlue);
                break;
            case 5:
                changeDiceImageAndText(5);
                changeTile(5, playerBlue);
                break;
            case 6:
                changeDiceImageAndText(6);
                changeTile(6, playerBlue);
                break;
            default:
                changeDiceImageAndText(0);
        }

    }

}

