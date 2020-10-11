package model;

import controller.CellController;
import controller.GameController;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tingying He on 2020/10/11.
 */
public class GameModel {
    private GameController gameController;

    public CellController[][] cellControllers;

    public double starRate = 0.1;
    public double clockRate = 0.1;


    public int spentTime = 0;
    public int totalTime =60;
    public int remainTime = totalTime;

    RotateTransition starRotateTransition;

    public GameModel(GameController gameController){
        this.gameController = gameController;

        starRotateTransition = new RotateTransition(Duration.millis(100), gameController.gameView.starIconImgView);
        starRotateTransition.setFromAngle(-30);
        starRotateTransition.setToAngle(30);
        starRotateTransition.setCycleCount(Timeline.INDEFINITE);
        starRotateTransition.setAutoReverse(true);
    }

    public void putNeighborMinesNum() {

        for (int i = 0; i < gameController.N; i++) {
            for (int j = 0; j < gameController.M; j++) {

                if (cellControllers[i][j].cellModel.isMine()) {
                    cellControllers[i][j].cellModel.neighborMinesNum = -1;
                }

                cellControllers[i][j].cellModel.neighborMinesNum = 0;

                {
                    for (int ii = i - 1; ii <= i + 1; ii++)
                        for (int jj = j - 1; jj <= j + 1; jj++)
                            if (isInMinefield(ii, jj)
                                    && cellControllers[ii][jj].cellModel.isMine())
                                cellControllers[i][j].cellModel.neighborMinesNum++;
                }
            }
        }
    }

    public void putStars(double starRate){
        for (int i = 0; i < gameController.N; i++) {
            for (int j = 0; j < gameController.M; j++) {
                if (cellControllers[i][j].cellModel.neighborMinesNum == 0 &&
                        !cellControllers[i][j].cellModel.isMine() &&
                        !cellControllers[i][j].cellModel.isClock() &&
                        Math.random() < starRate
                ) {
                    cellControllers[i][j].cellModel.setStar(true);
                }
            }
        }
    }

    public void putClocks(double clockRate){
        for (int i = 0; i < gameController.N; i++) {
            for (int j = 0; j < gameController.M; j++) {
                if (cellControllers[i][j].cellModel.neighborMinesNum == 0 &&
                        !cellControllers[i][j].cellModel.isMine() &&
                        !cellControllers[i][j].cellModel.isStar() &&
                        Math.random() < clockRate
                ) {
                    cellControllers[i][j].cellModel.setClock(true);
                }
            }
        }
    }

    public void addEventHandler() {
        for (int i = 0; i < gameController.N; i++) {
            for (int j = 0; j < gameController.M; j++) {
                int a = i;
                int b = j;
                cellControllers[i][j].cellView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                                    updateModel(a, b, true);
                                    System.out.println(cellControllers[a][b].cellModel.getNeighborMinesNum());
                                }
                                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                                    updateModel(a, b, false);
                                }
                            }
                        }
                );
            }
        }
    }

    public void updateModel(int i, int j, boolean left) {
        if (left) {
            if(!cellControllers[i][j].cellModel.isOpen()) {
                openCell(i, j);
            }
            if (cellControllers[i][j].cellModel.isMine()) {
//                System.out.println("Game Over");
                loseGame(gameController.gameView.starNumber, spentTime,gameController.gameView.openedCellsNumber,"You clicked on a mine :(");
            }

            if (cellControllers[i][j].cellModel.isClock()) {
//                gameController.gameView.timer.cancel();
                gameController.gameModel.remainTime = gameController.gameModel.remainTime + 10;
//                setTimer();
                cellControllers[i][j].cellModel.removeClock();

                ScaleTransition clockScaleTransition = new ScaleTransition(Duration.millis(300), gameController.gameView.clockIconImgView);
                clockScaleTransition.setFromX(0.8); // original x
                clockScaleTransition.setFromY(0.8); // original y
                clockScaleTransition.setToX(1); // final x is 25 times the original
                clockScaleTransition.setToY(1); // final y is 25 times the original
//                clockScaleTransition.setCycleCount(Timeline.INDEFINITE);
                clockScaleTransition.setAutoReverse(true);
                clockScaleTransition.play();


            }
        } else {
            cellControllers[i][j].cellModel.setFlag();


            gameController.gameView.remainMines --;
            gameController.gameView.remainMinesLabel.setText(gameController.gameView.remainMines+"/"+gameController.gameView.minesTotalNumber);
        }
        checkWin(gameController.gameView.starNumber, spentTime);
        cellControllers[i][j].cellView.init(cellControllers[i][j]);
    }

    //Flood-fill
    public void openCell(int i, int j) {
        cellControllers[i][j].cellModel.setOpen();
//        System.out.println(cellControllers[i][j].cellModel.getNumbers());
        gameController.gameView.openedCellsNumber ++;
        gameController.gameView.openedCellsNumberLabel.setText(gameController.gameView.openedCellsNumber +"/"+ gameController.N*gameController.M);

        if (cellControllers[i][j].cellModel.isStar()) {
//                System.out.println("Star!");


            cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
//                    System.out.println("Drag detected");
                    starRotateTransition.play();

                    cellControllers[i][j].cellModel.removeStar();
                    cellControllers[i][j].cellView.init(cellControllers[i][j]);

                    gameController.gameView.starNumber++;
                    gameController.gameView.starNumberLabel.setText(Integer.toString(gameController.gameView.starNumber));

                }
            });



            cellControllers[i][j].cellView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
//                    System.out.println("stop");
                    starRotateTransition.stop();
                }
            });
        }
        if (cellControllers[i][j].cellModel.getNeighborMinesNum() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++) {
                for (int jj = j - 1; jj <= j + 1; jj++) {
                    if (isInMinefield(ii, jj) &&
                            !cellControllers[ii][jj].cellModel.isOpen() &&
                            !cellControllers[ii][jj].cellModel.isMine()) {
                        openCell(ii, jj);
                        cellControllers[ii][jj].cellView.init(cellControllers[ii][jj]);
                    }
                }
            }
        }
    }

    public void setTimer() {
        try {
            gameController.gameView.timer = new Timer();
            gameController.gameView.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameController.gameView.timeLabel.setText(gameController.gameModel.remainTime + "s");
                        }
                    });
                    gameController.gameModel.spentTime++;
                    gameController.gameModel.remainTime--;
                    if (gameController.gameModel.remainTime == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Time up");
                                loseGame(gameController.gameView.starNumber, spentTime,gameController.gameView.openedCellsNumber,"Time is up!");
                            }
                        });
                    }
                }
            }, 0, 1000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Reference:https://stackoverflow.com/questions/34882640/java-count-down-timer-using-javafx

    public void openAll() {
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                cellControllers[i][j].cellModel.setOpen();
                cellControllers[i][j].cellView.init(cellControllers[i][j]);
            }
    }

    public void loseGame(int starNum, int spentTime,int openedCellsNumber, String whyLoseGame) {
        openAll();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Restart");
        alert.setTitle("GAME OVER");
        alert.setHeaderText("☆ Your Star(s): "+ starNum +" ☆");
        alert.setContentText(whyLoseGame+"\n\nTime spent in game: "+ spentTime + "s \nNumber of cells opened: "+openedCellsNumber);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                setTimer();
                restart();
            }
        });
        gameController.gameView.timer.cancel();
    }

    public void checkWin(int starNum,int spentTime) {
        if (winGame()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("YOU WIN!");
            alert.setHeaderText("☆ Your Star(s): "+ starNum +" ☆");
            alert.setContentText("Congrats!\nTime spent in game: "+ spentTime + "s");
            alert.show();
        }
    }

    public boolean winGame() {
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                if (cellControllers[i][j].cellModel.isMine()) {
                    if (!cellControllers[i][j].cellModel.isFlag()) {
                        return false;
                    }
                } else {
                    if (!cellControllers[i][j].cellModel.isOpen()) {
                        return false;
                    }
                }}
        return true;
    }

    public void restart(){
        gameController.gameView.init(gameController.N,gameController.M);
    }

    public boolean isInMinefield(int i, int j){
        if(i >= 0 && i < gameController.N && j >= 0 && j < gameController.M){
            return true;
        }else{
            return false;
        }
    }
}
