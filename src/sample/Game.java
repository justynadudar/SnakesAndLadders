package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements Runnable{

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
    private ImageView playerYellowImage;

    @FXML
    private Text textDiceValue;

    @FXML
    private Text textBeforeValue;

    ArrayList<Tile> board = new ArrayList<>();

    Player playerBlue;
    Player playerYellow;

    private String ip = "localhost";
    private int port = 3000;
    private Scanner scanner = new Scanner(System.in);
    private Thread thread;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private boolean accepted = false;
    private boolean yourTurn = false;
    private boolean enemyTurn = true;

    public Game(){
        initializeBoard();
        System.out.println("Please input the IP: ");
//        ip = scanner.nextLine();
        ip = "localhost";
        System.out.println("Please input the port: ");
//        port = scanner.nextInt();
        port = 3000;
        while (port < 1 || port > 65535) {
            System.out.println("The port you entered was invalid, please input another port: ");
            port = scanner.nextInt();
        }

        if (!connect()) initializeServer();
        thread = new Thread(this, "Game");
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            opponentMove();
        if (!enemyTurn && !accepted) {
                listenForServerRequest();
            }
        }
    }

    private void opponentMove() {
        System.out.println();
        if (!yourTurn) {
            try {
                int enemyDiceValue = dis.readInt();
                changeDiceImageAndText(enemyDiceValue);
                changeTile(enemyDiceValue, playerYellow);
                textBeforeValue.setFill(Color.rgb(251, 186, 19));
                textDiceValue.setFill(Color.rgb(251, 186, 19));
                textBeforeValue.setLayoutX(475);
                textDiceValue.setLayoutX(665);
                textBeforeValue.setText("Opponent rolled:");
                yourTurn = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            playerYellow = new Player(playerYellowImage);
            playerYellowImage.setVisible(true);
            playerYellow.setTileThePlayerIsOn(board.get(0));
            playerYellow.getPlayerImage().setLayoutX(board.get(0).getX());
            playerYellow.getPlayerImage().setLayoutY(board.get(0).getY());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        } catch (Exception e) {
            e.printStackTrace();
        }
        yourTurn = true;
        enemyTurn = false;
    }

    private void initializeBoard(){
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
    }

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
        dice.setImage(Dice.getDiceImages().get(0));

        if(!enemyTurn && !accepted){
            textBeforeValue.setText("Waiting...");
            playerBlue = new Player(playerBlueImage);
            playerBlueImage.setVisible(true);
            playerBlue.setTileThePlayerIsOn(board.get(0));
            playerBlue.getPlayerImage().setLayoutX(board.get(0).getX());
            playerBlue.getPlayerImage().setLayoutY(board.get(0).getY());
        }else{
            textBeforeValue.setText("  Your move!");
            playerYellow = new Player(playerYellowImage);
            playerYellowImage.setVisible(true);
            playerBlue = new Player(playerBlueImage);
            playerBlueImage.setVisible(true);
            playerBlue.setTileThePlayerIsOn(board.get(0));
            playerYellow.setTileThePlayerIsOn(board.get(0));

            playerBlue.getPlayerImage().setLayoutX(board.get(0).getX());
            playerBlue.getPlayerImage().setLayoutY(board.get(0).getY());
            playerYellow.getPlayerImage().setLayoutX(board.get(0).getX());
            playerYellow.getPlayerImage().setLayoutY(board.get(0).getY());
        }
    }

    @FXML
    void exitClicked(ActionEvent event) {

        //TODO POP UP TO ASK "Do you really want to EXIT?"

        Platform.exit();
        System.exit(0);
    }

    @FXML
    void rollClicked(ActionEvent event) {

        if(accepted){
            Player player = playerBlue;;
            if(yourTurn) {
                textBeforeValue.setFill(Color.rgb(63, 44, 182));
                textDiceValue.setFill(Color.rgb(63, 44, 182));
                textDiceValue.setVisible(true);
                textBeforeValue.setLayoutX(505);
                textDiceValue.setLayoutX(630);
                textBeforeValue.setText("You rolled:");

                int a = Dice.getRandomValue();
                switch (a){
                    case 1:
                        changeDiceImageAndText(1);
                        changeTile(1, player);
                        break;
                    case 2:
                        changeDiceImageAndText(2);
                        changeTile(2, player);
                        break;
                    case 3:
                        changeDiceImageAndText(3);
                        changeTile(3, player);
                        break;
                    case 4:
                        changeDiceImageAndText(4);
                        changeTile(4, player);
                        break;
                    case 5:
                        changeDiceImageAndText(5);
                        changeTile(5, player);
                        break;
                    case 6:
                        changeDiceImageAndText(6);
                        changeTile(6, player);
                        break;
                    default:
                        changeDiceImageAndText(0);
                }
                yourTurn = false;
                try {
                    dos.writeInt(a);
                    dos.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}

