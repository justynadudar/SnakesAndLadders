package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.lang.System.exit;

public class Main extends Application {

    public static final int tileSize = 45;
    private static final int tilesInRow = 10;
    private static final int tilesInColumn = 10;
    private static boolean gameStarted = false;

    Player player1;
    Player player2;

    public int circlePosition[][] = new int[10][10];

    private Group tilesGroup = new Group();
    public Button gameButton;
    public Label diceResult;
    public Label gameInfo;

    private int getRandomValue(){
        return (int)(Math.random() * 6 + 1);
    }

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(tilesInRow * tileSize + 350, tilesInColumn * tileSize);
        root.getChildren().addAll(tilesGroup);

        for(int i = 0; i < tilesInRow; i++){
            for(int j = 0; j < tilesInRow; j++){
                Tile tile = new Tile(tileSize, tileSize);
                tile.setTranslateX(tileSize * j);
                tile.setTranslateY(tileSize * i);

                tilesGroup.getChildren().add(tile);
            }
        }

        diceResult = new Label("0");
        diceResult.setTranslateX(635);
        diceResult.setTranslateY(67.5);

        gameInfo = new Label("Start a game!");
        gameInfo.setTranslateX(607.5);
        gameInfo.setTranslateY(77.5);

        player1 = new Player(22.5, "player1");
        player2 = new Player(22.5, "player2");

        gameButton = new Button("Start Game");
        gameButton.setTranslateX(600);
        gameButton.setTranslateY(25);
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    gameStarted = false;
                    exit(0);
                } else {
                    gameStarted = true;
                    System.out.println("Game started!");
                    gameButton.setText("Exit Game");

                    if((int)(Math.random() * 2 + 1) == 1) {
                        player1.changePlayerTurn();
                        gameInfo.setText("Player1 turn!");
                    }
                    else {
                        player2.changePlayerTurn();
                        gameInfo.setText("Player2 turn!");
                    }
                }
            }
        });

        Button button1 = new Button("Player 1");
        button1.setTranslateX(500);
        button1.setTranslateY(180);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(player1.isPlayerTurn()){
                        gameInfo.setText("Player2 turn!");

                        int random = getRandomValue();

                        diceResult.setText(String.valueOf(random));

                        player1.changePlayerTurn();
                        player2.changePlayerTurn();
                    }
                }
            }
        });

        Button button2 = new Button("Player 2");
        button2.setTranslateX(500);
        button2.setTranslateY(405);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(player2.isPlayerTurn()){
                        gameInfo.setText("Player1 turn!");

                        int random = getRandomValue();

                        diceResult.setText(String.valueOf(random));

                        player1.changePlayerTurn();
                        player2.changePlayerTurn();
                    }
                }
            }
        });

        tilesGroup.getChildren().addAll(player1, player2, button1, button2, gameButton, diceResult, gameInfo);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
            Scene scene = new Scene(createContent());
            primaryStage.setTitle("Snakes and Ladders");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
