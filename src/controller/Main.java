package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    int N = 16;
    int M = 16;



    @Override
    public void start(Stage primaryStage) throws Exception{
//        GridController gridController = new GridController(N,M);
        GameController gameController = new GameController(N,M);


//        Parent root = FXMLLoader.load(getClass().getResource("../sample/sample.fxml"));
        primaryStage.setTitle("Tingying He");
        primaryStage.setScene(new Scene(gameController.gameView, 800, 700));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}



