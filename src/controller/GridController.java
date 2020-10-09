package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    private int remainTime = 10;


    public CellController[][] cellControllers;

    public GridController(int N, int M) {
        this.N = N;
        this.M = M;
        this.cellControllers = new CellController[N][M];

        createCellsGrid();
        NeighborMinesNumbers();
        addEventHandler();

        setTimer();
        gridPane.add(timeLabel,21,21,1,1);


    }

    private void createCellsGrid(){
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
                            if (ii > 0 && ii < 20 && jj > 0 && jj < 20 && cellControllers[ii][jj].cellModel.isMine())
                                cellControllers[i][j].cellModel.numbers++;
                }
            }
    }

    private void addEventHandler(){
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
            }
    }

    public void updateModel(int i, int j, boolean left) {
        if (left) {
            openCell(i, j);
            if(cellControllers[i][j].cellModel.isMine()){
                System.out.println("Game Over");
            }
            if(cellControllers[i][j].cellModel.isStar()){
                System.out.println("Star!");
            }
            if(cellControllers[i][j].cellModel.isClock()){
                remainTime = remainTime +30;
                setTimer();
            }
        } else {
            cellControllers[i][j].cellModel.setFlag();
        }
        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
    }

    //Flood-fill
    public void openCell(int i, int j) {
        cellControllers[i][j].cellModel.setOpen();
//        System.out.println(cellControllers[i][j].cellModel.getNumbers());

        if (cellControllers[i][j].cellModel.getNumbers() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++)
                for (int jj = j - 1; jj <= j + 1; jj++)
                    if (ii > 0 && ii < 20 && jj > 0 && jj < 20 && !cellControllers[ii][jj].cellModel.open && !cellControllers[ii][jj].cellModel.isMine()) {
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
}



