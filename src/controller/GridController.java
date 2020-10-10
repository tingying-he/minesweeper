package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.GridModel;
import view.CellView;
import view.GridView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class GridController {
    private int N, M;
    GridPane gridPane = new GridPane();
    public Timer timer;
    public Label timeLabel = new Label();
    private int remainTime = 1000;

    //star
    public Pane starPane = new Pane();
    public int starNumber = 0;
    public Label starNumberLabel = new Label();


    public CellController[][] cellControllers;

    public GridController(int N, int M) {
        this.N = N;
        this.M = M;
        this.cellControllers = new CellController[N][M];
        starNumberLabel.setText(Integer.toString(starNumber));

        createCellsGrid();
        NeighborMinesNumbers();
        addEventHandler();

        setTimer();
        gridPane.add(timeLabel, 21, 21, 1, 1);

        starPane.getChildren().add(starNumberLabel);
        starPane.setStyle("-fx-background-color: YELLOW;");
        starPane.setPrefSize(32, 32);
        gridPane.add(starPane, 22, 21, 1, 1);

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
                loseGame();
            }
            if (cellControllers[i][j].cellModel.isStar()) {
                System.out.println("Star!");
                cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("Drag detected");
                        cellControllers[i][j].cellModel.moveStar();
                        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
                        starNumber++;
                        starNumberLabel.setText(Integer.toString(starNumber));

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
                        timeLabel.setText("Remain Time:" + remainTime);
                    });
                    remainTime--;
                } else {
                    timer.cancel();
                    System.out.println("Time up");
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

    public void loseGame() {
        openAll();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Lose Game");
        alert.setHeaderText("Lose Game");
        alert.setContentText("Lose Game");
        alert.show();
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
}



