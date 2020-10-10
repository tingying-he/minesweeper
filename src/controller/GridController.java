package controller;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.CellModel;
import model.GridModel;
import view.CellView;
import view.GridView;


import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class GridController {
    private int N, M;
    HBox gameBox = new HBox();
    VBox settingBox = new VBox();

    GridPane gridPane = new GridPane();

    //time
    public Timer timer;
    public Label timeLabel = new Label();
    private int remainTime = 1000;

    //number of opened cells
    private int openedCellsNumber = 0;
    public Label openedCellsNumberLabel = new Label();

    //star
    AnchorPane starPane = new AnchorPane();
    public int starNumber = 0;
    public Label starNumberLabel = new Label();
    Image starIconImg = new Image(CellModel.starImgURL);
    ImageView starIconImgView = new ImageView(starIconImg);
    public Label starIntroLabel = new Label("drag to collect stars");


    //restart
    public Button restartBtn = new Button("restart");


    public CellController[][] cellControllers;

    public GridController(int N, int M){
        this.N = N;
        this.M = M;


        gameBox.getChildren().add(gridPane);
        gameBox.getChildren().add(settingBox);

        settingBox.getChildren().addAll(starPane,timeLabel, openedCellsNumberLabel,restartBtn);



        //starPane
        starPane.setStyle("-fx-background-color: YELLOW;");
        starPane.setPrefSize(32, 50);
        starIntroLabel.setLayoutX(10);
        starIntroLabel.setLayoutY(50);
        //starPane text
        starNumberLabel.setFont(new Font("Arial", 30));
        starNumberLabel.setLayoutX(60);
        starNumberLabel.setLayoutY(10);
        //starpane animation
        starIconImgView.setFitHeight(30);
        starIconImgView.setFitWidth(30);
        starIconImgView.setX(10);
        starIconImgView.setY(10);

        starPane.getChildren().addAll(starNumberLabel,starIconImgView,starIntroLabel);


        init(N,M);
        restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            init(N,M);
        });






    }
    public void init(int N, int M) {






        this.cellControllers = new CellController[N][M];

        setTimer();

        starNumber = 0;

        starNumberLabel.setText(Integer.toString(starNumber));

        openedCellsNumber = 0;

        openedCellsNumberLabel.setText(Integer.toString(openedCellsNumber));


        createCellsGrid();
        NeighborMinesNumbers();
        addEventHandler();




    }

    private void createCellsGrid() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                cellControllers[i][j] = new CellController(i, j);
                gridPane.add(cellControllers[i][j].cellView, i, j, 1, 1);
            }
    }

    private void NeighborMinesNumbers() {

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {

                if (cellControllers[i][j].cellModel.isMine()) {
                    cellControllers[i][j].cellModel.numbers = -1;
                }

                cellControllers[i][j].cellModel.numbers = 0;

                {
                    for (int ii = i - 1; ii <= i + 1; ii++)
                        for (int jj = j - 1; jj <= j + 1; jj++)
                            if (ii > 0 && ii < N && jj > 0 && jj < M && cellControllers[ii][jj].cellModel.isMine())
                                cellControllers[i][j].cellModel.numbers++;
                }
            }
    }

    private void addEventHandler() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                int a = i;
                int b = j;
                cellControllers[i][j].cellView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                                    updateModel(a, b, true);
                                    System.out.println(cellControllers[a][b].cellModel.getNumbers());
                                }
                                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                                    updateModel(a, b, false);
                                }
                            }
                        }

                );

                if (cellControllers[i][j].cellModel.isStar() && cellControllers[i][j].cellModel.open) {

                }
            }
    }

    public void updateModel(int i, int j, boolean left) {
        if (left) {
            openCell(i, j);
            if (cellControllers[i][j].cellModel.isMine()) {
                System.out.println("Game Over");
                loseGame("You click a mine");
            }
            if (cellControllers[i][j].cellModel.isStar()) {
                System.out.println("Star!");
                ScaleTransition st = new ScaleTransition(Duration.millis(300), starIconImgView);

                st.setFromX(1); // original x
                st.setFromY(1); // original y
                st.setToX(1.4); // final x is 25 times the original
                st.setToY(1.4); // final y is 25 times the original
                st.setCycleCount(Timeline.INDEFINITE);
                st.setAutoReverse(true);
                cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("Drag detected");
                        st.play();
                        cellControllers[i][j].cellModel.moveStar();
                        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
                        starNumber++;
                        starNumberLabel.setText(Integer.toString(starNumber));

                    }
                });


                cellControllers[i][j].cellView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("stop");
                        st.stop();
                    }
                });
            }
            if (cellControllers[i][j].cellModel.isClock()) {
                remainTime = remainTime + 30;
                setTimer();
                cellControllers[i][j].cellModel.moveClock();
            }
        } else {
            cellControllers[i][j].cellModel.setFlag();
        }
        winGame();
        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
    }

    //Flood-fill
    public void openCell(int i, int j) {
        cellControllers[i][j].cellModel.setOpen();
//        System.out.println(cellControllers[i][j].cellModel.getNumbers());
        openedCellsNumber ++;
        openedCellsNumberLabel.setText(Integer.toString(openedCellsNumber));

        if (cellControllers[i][j].cellModel.getNumbers() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++)
                for (int jj = j - 1; jj <= j + 1; jj++)
                    if (ii > 0 && ii < N && jj > 0 && jj < M && !cellControllers[ii][jj].cellModel.open && !cellControllers[ii][jj].cellModel.isMine()) {
                        openCell(ii, jj);
                        cellControllers[ii][jj].cellView.init(cellControllers[ii][jj].cellModel);
//            System.out.println(cellControllers[i][j].cellModel.getNumbers());
                    }
        }
    }

    public void setTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (remainTime > 0) {
                    Platform.runLater(() -> {
                        timeLabel.setText("Remain Time:\n" + remainTime);
                    });
                    remainTime--;
                } else {
                    timer.cancel();
                    System.out.println("Time up");
                    loseGame("Time up");
                }
            }
        }, 0, 1000);
    }
    //Reference:https://stackoverflow.com/questions/47655695/javafx-countdown-timer-in-label-settext

    public void openAll() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                cellControllers[i][j].cellModel.setOpen();
                cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
            }
    }

    public void loseGame(String whyLoseGame) {
        openAll();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lose Game");
        alert.setHeaderText("Lose Game");
        alert.setContentText(whyLoseGame);
        alert.show();
        timer.cancel();
    }

    public void winGame() {
        if (checkWin()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Win Game");
            alert.setHeaderText("Win Game");
            alert.setContentText("Win Game");
            alert.show();
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                if (cellControllers[i][j].cellModel.isMine()) {
                    if (!cellControllers[i][j].cellModel.flag) {
                        return false;
                    }
                } else {
                    if (!cellControllers[i][j].cellModel.open) {
                        return false;
                    }
                }}
                return true;
    }

    public void restart(){
        init(N,M);
    }
}



