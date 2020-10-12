package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private int N = 16;
    private int M = 16;

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameController gameController = new GameController(N,M);

        primaryStage.setTitle("Minesweeper_Tingying He");
        primaryStage.setScene(new Scene(gameController.gameView, 800, 680));
        primaryStage.show();

        //stop application on window close
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


