package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.CellModel;
import view.CellView;


public class Main extends Application {
    int N = 20;
    int M = 20;

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane gamePane = new GridPane();
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++) {
                CellController cellController = new CellController(i,j);
                gamePane.add(cellController.cellView,i,j,1,1);
            }
        }


//        Parent root = FXMLLoader.load(getClass().getResource("../sample/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(gamePane, 1000, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
