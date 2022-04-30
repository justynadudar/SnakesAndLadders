package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int tileSize = 45;
    private static final int tilesInRow = 10;
    private static final int tilesInColumn = 10;

    private Group tilesGroup = new Group();

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
