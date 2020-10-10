package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.CellModel;
import model.GridModel;
import view.CellView;
import view.GridView;


public class Main extends Application {
    int N = 20;
    int M = 20;



    @Override
    public void start(Stage primaryStage) throws Exception{
        GridController gridController = new GridController(N,M);


//        Parent root = FXMLLoader.load(getClass().getResource("../sample/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(gridController.gridPane, 1000, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



