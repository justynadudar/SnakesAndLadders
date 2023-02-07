package com.example.snakesandladders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameController implements Runnable {

    @FXML
    private Label alert;

    @FXML
    private Label alertExit;

    @FXML
    private Label alertOpponentLeft;

    @FXML
    private Button btnAlertExitNo;

    @FXML
    private Button btnAlertExitYes;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRoll;

    @FXML
    private Button btnPlayAgain;

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

    @FXML
    private TextField ipInput;

    @FXML
    private TextField portInput;

    ArrayList<Tile> board = new ArrayList<> ();

    Player playerBlue;
    Player playerYellow;

    private String ip;
    private int port;
    private Thread thread;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private boolean accepted = false;
    private boolean yourTurn = false;
    private boolean enemyTurn = true;
    private boolean opponentLeft = false;

    Timeline timeline = new Timeline (new KeyFrame (Duration.millis (1500), ev -> {
        textBeforeValue.setFill (Color.rgb (251, 186, 19));
        dice.setImage (Dice.getDiceImages ().get (0));
        textDiceValue.setVisible (false);
        textBeforeValue.setLayoutX (490);
        textBeforeValue.setText ("Opponent move!");
    }));

    public GameController() {
        timeline.setCycleCount (1);
    }

    @Override
    public void run () {
        while (true) {
            if(!opponentLeft) opponentMove ();
            if (!enemyTurn && !accepted) {
                listenForServerRequest ();
            }
        }
    }

    private void opponentMove () {
        System.out.println("");
        int enemyDiceValue = 0;
        if (dis != null) {
            try {
                enemyDiceValue = dis.readInt ();
                if (enemyDiceValue == -1) {
                    alertOpponentLeft.setVisible (true);
                    TimeUnit.MILLISECONDS.sleep (2000);
                    alertOpponentLeft.setVisible (false);
                    textBeforeValue.setVisible (false);
                    textDiceValue.setVisible (false);
                    btnPlay.setVisible (false);
                    btnRoll.setVisible (false);
                    btnExit.setLayoutX (514);
                    btnExit.setLayoutY (216);
                    btnPlayAgain.setVisible (true);
                    dice.setVisible (false);
                    textDiceValue.setVisible (false);
                    opponentLeft = true;
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        if (!yourTurn && dis != null) {
            timeline.stop ();
            if (enemyDiceValue == 7) {
                playerYellow.getPlayerImage ().setLayoutX (board.get (99).getX ());
                playerYellow.getPlayerImage ().setLayoutY (board.get (99).getY ());
                textBeforeValue.setFill (Color.rgb (251, 186, 19));
                textDiceValue.setFill (Color.rgb (251, 186, 19));
                btnPlay.setVisible (false);
                btnRoll.setVisible (false);
                btnExit.setLayoutX (514);
                btnExit.setLayoutY (216);
                btnPlayAgain.setVisible (true);
                dice.setVisible (false);
                textDiceValue.setVisible (false);
                textBeforeValue.setLayoutX (525);
                textBeforeValue.setText ("You lose!");

            } else {
                changeDiceImageAndText (enemyDiceValue);
                changeTile (enemyDiceValue, playerYellow);
                textBeforeValue.setFill (Color.rgb (251, 186, 19));
                textDiceValue.setFill (Color.rgb (251, 186, 19));
                textBeforeValue.setLayoutX (475);
                textDiceValue.setLayoutX (665);
                textDiceValue.setVisible (true);
                textBeforeValue.setText ("Opponent rolled:");
                try {
                    TimeUnit.MILLISECONDS.sleep (1500);
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                textBeforeValue.setFill (Color.rgb (63, 44, 182));
                textBeforeValue.setLayoutX (505);
                textDiceValue.setVisible (false);
                dice.setImage (Dice.getDiceImages ().get (0));
                textBeforeValue.setText ("  Your move!");
                yourTurn = true;
            }
        }
    }

    private void listenForServerRequest () {
        Socket socket = null;
        try {
            socket = serverSocket.accept ();
            dos = new DataOutputStream (socket.getOutputStream ());
            dis = new DataInputStream (socket.getInputStream ());
            accepted = true;
            System.out.println ("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            playerYellow = new Player (playerYellowImage);
            playerYellowImage.setVisible (true);
            playerYellow.setTileThePlayerIsOn (board.get (0));
            playerYellow.getPlayerImage ().setLayoutX (board.get (0).getX ());
            playerYellow.getPlayerImage ().setLayoutY (board.get (0).getY ());
            textBeforeValue.setLayoutX (505);
            textBeforeValue.setText ("  Your move!");
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private boolean connect () {
        try {
            socket = new Socket (ip, port);
            dos = new DataOutputStream (socket.getOutputStream ());
            dis = new DataInputStream (socket.getInputStream ());
            accepted = true;
            enemyTurn = true;
        } catch (IOException e) {
            System.out.println ("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println ("Successfully connected to the server.");
        return true;
    }

    private void initializeServer () {
        try {
            serverSocket = new ServerSocket (port, 8, InetAddress.getByName (ip));
        } catch (Exception e) {
            e.printStackTrace ();
        }
        yourTurn = true;
        enemyTurn = false;
    }

    private void initializeBoard () {
        int x = 0;
        int y = 360;
        int operation = 1;

        for (int i = 1; i <= 100; i++) {
            board.add (new Tile (i, x * 40, y));
            if ((i) % 10 == 0 && (i) % 20 == 0) {
                x = 0;
                y -= 40;
                operation = 1;
            } else if ((i) % 20 == 10 && (i) % 10 == 0) {
                x = 9;
                y -= 40;
                operation = -1;
            } else x += operation;
        }

        LadderSnakeEndTile snake1 = new LadderSnakeEndTile (board.get (19));
        board.get (58).setSnake (snake1);
        LadderSnakeEndTile snake2 = new LadderSnakeEndTile (board.get (66));
        board.get (94).setSnake (snake2);
        LadderSnakeEndTile ladder1 = new LadderSnakeEndTile (board.get (28));
        board.get (6).setLadder (ladder1);
        LadderSnakeEndTile ladder2 = new LadderSnakeEndTile (board.get (43));
        board.get (35).setLadder (ladder2);
        LadderSnakeEndTile ladder3 = new LadderSnakeEndTile (board.get (51));
        board.get (47).setLadder (ladder3);
        LadderSnakeEndTile ladder4 = new LadderSnakeEndTile (board.get (81));
        board.get (63).setLadder (ladder4);
    }

    void changeDiceImageAndText (int value) {
        textDiceValue.setText (String.valueOf (value));
        dice.setImage (Dice.getDiceImages ().get (value));
    }

    void changeTile (int diceValue, Player player) {
        int previousTileIndex = board.indexOf (player.getTileThePlayerIsOn ());
        if (player.getTileThePlayerIsOn ().getId () == 1 && diceValue != 6) {
            player.getPlayerImage ().setLayoutX (board.get (0).getX ());
            player.getPlayerImage ().setLayoutY (board.get (0).getY ());
            textBeforeValue.setLayoutX (435);
            textBeforeValue.setText ("You have to roll 6 to start!");
            textDiceValue.setVisible (false);
            return;
        }

        if (previousTileIndex + diceValue > 99) player.setTileThePlayerIsOn (board.get (previousTileIndex));
        else player.setTileThePlayerIsOn (board.get (previousTileIndex + diceValue));

        if (player.getTileThePlayerIsOn ().getSnake () != null) {
            player.setTileThePlayerIsOn (player.getTileThePlayerIsOn ().getSnake ().endTile);
        } else if (player.getTileThePlayerIsOn ().getLadder () != null) {
            player.setTileThePlayerIsOn (player.getTileThePlayerIsOn ().getLadder ().endTile);
        }


        player.getPlayerImage ().setLayoutX (player.getTileThePlayerIsOn ().getX ());
        player.getPlayerImage ().setLayoutY (player.getTileThePlayerIsOn ().getY ());

        if (player.getTileThePlayerIsOn ().getId () == 100) {
            yourTurn = false;
            btnPlay.setVisible (false);
            btnRoll.setVisible (false);
            btnPlayAgain.setVisible (true);
            btnExit.setLayoutX (514);
            btnExit.setLayoutY (216);
            dice.setVisible (false);
            textDiceValue.setVisible (false);
            textBeforeValue.setText ("    You win!");
        }
    }

    @FXML
    void playClicked (ActionEvent event) {
        if (ipInput.getText ().length () == 0 || portInput.getText ().length () == 0) {
            alert.setVisible (true);
        } else {
            alert.setVisible (false);
            initializeBoard ();
            ip = ipInput.getText ();
            if (portInput.getText ().length () > 5) {
                alert.setPrefWidth (170);
                alert.setText ("The port you entered is invalid");
                alert.setVisible (true);
            } else {
                port = Integer.parseInt (portInput.getText ());
            }
            if (port < 1 || port > 65535) {
                alert.setPrefWidth (170);
                alert.setText ("The port you entered is invalid");
                alert.setVisible (true);
            } else {
                try {
                    InetAddress.getByName (ip);
                    if (!connect ()) initializeServer ();
                    thread = new Thread (this, "Game");
                    thread.start ();

                    btnPlay.setVisible (false);
                    btnRoll.setVisible (true);
                    btnExit.setVisible (true);
                    btnExit.setLayoutX (585);
                    btnExit.setLayoutY (335);
                    textBeforeValue.setVisible (true);
                    dice.setVisible (true);
                    dice.setImage (Dice.getDiceImages ().get (0));
                    ipInput.setVisible (false);
                    portInput.setVisible (false);

                    if (!enemyTurn && !accepted) {
                        textBeforeValue.setFill (Color.rgb (63, 44, 182));
                        textBeforeValue.setText ("Waiting for opponnent...");
                        textBeforeValue.setLayoutX (445);
                        playerBlue = new Player (playerBlueImage);
                        playerBlueImage.setVisible (true);
                        playerBlue.setTileThePlayerIsOn (board.get (0));
                        playerBlue.getPlayerImage ().setLayoutX (board.get (0).getX ());
                        playerBlue.getPlayerImage ().setLayoutY (board.get (0).getY ());
                    } else {
                        if (enemyTurn) {
                            textBeforeValue.setFill (Color.rgb (251, 186, 19));
                            textBeforeValue.setLayoutX (490);
                            textBeforeValue.setText ("Opponent move!");
                        } else {
                            textBeforeValue.setLayoutX (505);
                            textBeforeValue.setText ("  Your move!");
                        }
                        playerYellow = new Player (playerYellowImage);
                        playerYellowImage.setVisible (true);
                        playerBlue = new Player (playerBlueImage);
                        playerBlueImage.setVisible (true);
                        playerBlue.setTileThePlayerIsOn (board.get (0));
                        playerYellow.setTileThePlayerIsOn (board.get (0));

                        playerBlue.getPlayerImage ().setLayoutX (board.get (0).getX ());
                        playerBlue.getPlayerImage ().setLayoutY (board.get (0).getY ());
                        playerYellow.getPlayerImage ().setLayoutX (board.get (0).getX ());
                        playerYellow.getPlayerImage ().setLayoutY (board.get (0).getY ());
                    }
                } catch (UnknownHostException e) {
                    alert.setText ("The IP you entered is invalid");
                    alert.setVisible (true);
                }
            }
        }
    }

    @FXML
    void playAgainClicked (ActionEvent event) {
        textBeforeValue.setVisible (false);
        btnExit.setVisible (false);
        btnPlayAgain.setVisible (false);
        timeline.stop ();

        if (serverSocket != null) {
            try {
                serverSocket.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        if (socket != null) {
            try {
                socket.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        dis = null;
        dos = null;

        playerBlue = null;
        playerYellow = null;
        accepted = false;
        yourTurn = false;
        enemyTurn = true;

        ipInput.setVisible (true);
        portInput.setVisible (true);
        btnPlay.setVisible (true);
        thread.interrupt ();
    }

    @FXML
    void exitClicked (ActionEvent event) {
        alertExit.setVisible (true);
        btnAlertExitNo.setVisible (true);
        btnAlertExitYes.setVisible (true);
        btnRoll.setDisable (true);
        btnExit.setDisable (true);
    }

    @FXML
    void noClicked (ActionEvent event) {
        alertExit.setVisible (false);
        btnAlertExitNo.setVisible (false);
        btnAlertExitYes.setVisible (false);
        btnRoll.setDisable (false);
        btnExit.setDisable (false);
    }

    @FXML
    void yesClicked (ActionEvent event) {
        if (dos != null) {
            try {
                dos.writeInt (-1);
                dos.flush ();
            } catch (IOException e1) {
                e1.printStackTrace ();
            }
        }
        Platform.exit ();
        System.exit (0);
    }

    @FXML
    void rollClicked (ActionEvent event) {
        if (accepted) {
            Player player = playerBlue;
            if (yourTurn) {
                textBeforeValue.setFill (Color.rgb (63, 44, 182));
                textDiceValue.setFill (Color.rgb (63, 44, 182));
                textDiceValue.setVisible (true);
                textBeforeValue.setLayoutX (505);
                textDiceValue.setLayoutX (630);
                textBeforeValue.setText ("You rolled:");

                int a = Dice.getRandomValue ();
                switch (a) {
                    case 1:
                        changeDiceImageAndText (1);
                        changeTile (1, player);
                        break;
                    case 2:
                        changeDiceImageAndText (2);
                        changeTile (2, player);
                        break;
                    case 3:
                        changeDiceImageAndText (3);
                        changeTile (3, player);
                        break;
                    case 4:
                        changeDiceImageAndText (4);
                        changeTile (4, player);
                        break;
                    case 5:
                        changeDiceImageAndText (5);
                        changeTile (5, player);
                        break;
                    case 6:
                        changeDiceImageAndText (6);
                        changeTile (6, player);
                        break;
                    default:
                        changeDiceImageAndText (0);
                }
                if (yourTurn) {
                    yourTurn = false;
                    try {
                        dos.writeInt (a);
                        dos.flush ();
                    } catch (IOException e1) {
                        e1.printStackTrace ();
                    }
                    timeline.play ();
                } else {
                    try {
                        dos.writeInt (7);
                        dos.flush ();
                    } catch (IOException e1) {
                        e1.printStackTrace ();
                    }
                }
            }
        }
    }

}

