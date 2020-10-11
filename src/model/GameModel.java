package model;

import controller.CellController;
import controller.GameController;
import javafx.animation.RotateTransition;
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

    public GameModel(GameController gameController){
        this.gameController = gameController;
    }


    public void NeighborMinesNumbers() {

        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {

                if (cellControllers[i][j].cellModel.isMine()) {
                    cellControllers[i][j].cellModel.neighborMinesNum = -1;
                }

                cellControllers[i][j].cellModel.neighborMinesNum = 0;

                {
                    for (int ii = i - 1; ii <= i + 1; ii++)
                        for (int jj = j - 1; jj <= j + 1; jj++)
                            if (ii >= 0 && ii < gameController.N && jj >= 0 && jj < gameController.M && cellControllers[ii][jj].cellModel.isMine())
                                cellControllers[i][j].cellModel.neighborMinesNum++;
                }
            }
    }

    public void putStars(){
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                if(cellControllers[i][j].cellModel.neighborMinesNum == 0 &&
                        !cellControllers[i][j].cellModel.isMine() &&
                        !cellControllers[i][j].cellModel.isClock() &&
                        Math.random()<0.03
                ){
                    cellControllers[i][j].cellModel.setStar(true);
                }
            }
    }

    public void putClocks(){
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                if(cellControllers[i][j].cellModel.neighborMinesNum == 0 &&
                        !cellControllers[i][j].cellModel.isMine() &&
                        !cellControllers[i][j].cellModel.isStar() &&
                        Math.random()<0.03
                ){
                    cellControllers[i][j].cellModel.setClock(true);
                }
            }
    }

    public void addEventHandler() {
        for (int i = 0; i < gameController.N; i++)
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

                if (cellControllers[i][j].cellModel.isStar() && cellControllers[i][j].cellModel.isOpen()) {

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
//                ScaleTransition st = new ScaleTransition(Duration.millis(300), starIconImgView);

                RotateTransition starRotateTransition =
                        new RotateTransition(Duration.millis(100), gameController.gameView.starIconImgView);
                starRotateTransition.setFromAngle(-30);
                starRotateTransition.setToAngle(30);
                starRotateTransition.setCycleCount(Timeline.INDEFINITE);
                starRotateTransition.setAutoReverse(true);
//                starRotateTransition.play();

                cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("Drag detected");
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
                        System.out.println("stop");
                        starRotateTransition.stop();
                    }
                });
            }
            if (cellControllers[i][j].cellModel.isClock()) {
                gameController.gameView.timer.cancel();
                gameController.gameView.remainTime = gameController.gameView.remainTime + 30;
                setTimer();
                cellControllers[i][j].cellModel.removeClock();

            }
        } else {
            cellControllers[i][j].cellModel.setFlag();
            gameController.gameView.remainMines --;
            gameController.gameView.remainMinesLabel.setText(gameController.gameView.remainMines+"/"+gameController.gameView.minesTotalNumber);
        }
        winGame();
        cellControllers[i][j].cellView.init(cellControllers[i][j]);
    }

    //Flood-fill
    public void openCell(int i, int j) {
        cellControllers[i][j].cellModel.setOpen();
//        System.out.println(cellControllers[i][j].cellModel.getNumbers());
        gameController.gameView.openedCellsNumber ++;
        gameController.gameView.openedCellsNumberLabel.setText(gameController.gameView.openedCellsNumber +"/"+ gameController.N*gameController.M);

        if (cellControllers[i][j].cellModel.getNeighborMinesNum() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++)
                for (int jj = j - 1; jj <= j + 1; jj++)
                    if (ii >= 0 && ii < gameController.N && jj >= 0 && jj < gameController.M && !cellControllers[ii][jj].cellModel.isOpen() && !cellControllers[ii][jj].cellModel.isMine()) {
                        openCell(ii, jj);
                        cellControllers[ii][jj].cellView.init(cellControllers[ii][jj]);

//            System.out.println(cellControllers[i][j].cellModel.getNumbers());
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
                            gameController.gameView.timeLabel.setText(gameController.gameView.remainTime + "s");

                        }
                    });
                    gameController.gameView.remainTime--;
                    if (gameController.gameView.remainTime == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Time up");
                                loseGame("Time up");
                            }
                        });

                    }

                }
            }, 0, 1000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openAll() {
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                cellControllers[i][j].cellModel.setOpen();
                cellControllers[i][j].cellView.init(cellControllers[i][j]);
            }
    }

    public void loseGame(String whyLoseGame) {
        openAll();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Restart");
        alert.setTitle("Lose Game");
        alert.setHeaderText("Lose Game");
        alert.setContentText(whyLoseGame);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                setTimer();
                restart();
            }
        });
        gameController.gameView.timer.cancel();
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
}
