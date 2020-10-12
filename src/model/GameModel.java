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

    public double starRate = 0.12;
    public double clockRate = 0.12;

    //timer
    public Timer timer;
    public int spentTime = 0;
    public int totalTime =60;
    public int remainTime = totalTime;

    public int openedCellsNum = 0;
    public int starNumber = 0;
    public int minesTotalNumber = 0;
    public int remainMines = 0;
//    public int flaggedMines = 0;



    public GameModel(GameController gameController){
        this.gameController = gameController;


    }

    public void init(){
        remainTime = totalTime;
        starNumber = 0;
        openedCellsNum = 0;

        putNeighborMinesNum();
        putStars(starRate);
        putClocks(clockRate);
        gameController.addEventHandler();
        minesTotalNumber = 0;
        for (int i = 0; i < gameController.N; i++) {
            for (int j = 0; j < gameController.M; j++) {
                if (cellControllers[i][j].cellModel.isMine()) {
                    minesTotalNumber++;
                }
            }
        }
        remainMines = minesTotalNumber;

    }

    //calculate neighboring mines number for each cell
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

    //put stars into minefield grid. Stars are only on cells whose don't have neighboring mine
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

    //put clocks into minefield grid. Stars are only on cells whose don't have neighboring mine
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



    public void loseGame(int starNum, int spentTime,int openedCellsNumber, String whyLoseGame) {

        timer.cancel();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Restart");
        alert.setTitle("GAME OVER");
        alert.setHeaderText("☆ Your Star(s): "+ starNum +" ☆");
        alert.setContentText(whyLoseGame+"\n\nTime spent in game: "+ spentTime + "s \nNumber of cells you opened: "+openedCellsNumber);


        openAll();

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                gameController.setTimer();

                gameController.init(gameController.N, gameController.M);//restart
            }
        });

    }

    public void checkWin(int starNum,int spentTime) {
        if (winGame()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Restart");
            alert.setTitle("YOU WIN!");
            alert.setHeaderText("☆ Your Star(s): "+ starNum +" ☆");
            alert.setContentText("Congrats!\n\nTime spent in game: "+ spentTime + "s");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    gameController.setTimer();

                    gameController.init(gameController.N, gameController.M);//restart
                }
            });
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

    public boolean isInMinefield(int i, int j){
        if(i >= 0 && i < gameController.N && j >= 0 && j < gameController.M){
            return true;
        }else{
            return false;
        }
    }

    //open all cells
    public void openAll() {
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                cellControllers[i][j].cellModel.setOpen(true);
                cellControllers[i][j].init();
            }
    }


}
