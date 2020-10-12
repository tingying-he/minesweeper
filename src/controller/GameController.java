package controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.GameModel;
import view.GameView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tingying He on 2020/10/11.
 */
public class GameController {
    public int N, M;
    public GameView gameView = new GameView(this);
    public GameModel gameModel = new GameModel(this);

    public GameController(int N, int M) {
        this.N = N;
        this.M = M;

        setTimer();
        init(N, M);

        //addEventHandler to buttons on gameView
        gameView.restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            init(N, M);//restart
        });
        gameView.exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    //restart
    public void init(int N, int M){

        gameModel.cellControllers = new CellController[N][M];
        createCellsGrid();
        gameModel.init();
        gameView.starNumberLabel.setText(Integer.toString(gameModel.starNumber));
        gameView.openedCellsNumLabel.setText(gameModel.openedCellsNum +"/"+ N*M);
        gameView.remainMinesLabel.setText(gameModel.remainMines+"/"+gameModel.minesTotalNumber);
    }

    private void createCellsGrid() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                gameModel.cellControllers[i][j] = new CellController();
                gameView.gridPane.add(gameModel.cellControllers[i][j].cellView, i, j, 1, 1);
            }
    }

    public void addEventHandler() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int a = i;
                int b = j;
                gameModel.cellControllers[i][j].cellView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                                    updateModel(a, b, true);
                                    System.out.println(gameModel.cellControllers[a][b].cellModel.getNeighborMinesNum());
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

    //If a cell is clicked, the model will be updated
    public void updateModel(int i, int j, boolean left) {
        if (left) {
            if(!gameModel.cellControllers[i][j].cellModel.isOpen()) {
                openCell(i, j);
            }
            if (gameModel.cellControllers[i][j].cellModel.isMine()) {
                gameModel.loseGame(
                        gameModel.starNumber,
                        gameModel.spentTime,
                        gameModel.openedCellsNum,
                        "You clicked on a mine :(");
            }

            if (gameModel.cellControllers[i][j].cellModel.isClock()) {
                gameModel.remainTime = gameModel.remainTime + 15;
                gameModel.cellControllers[i][j].cellModel.removeClock();

                ScaleTransition clockScaleTransition = new ScaleTransition(Duration.millis(300), gameView.clockIconImgView);
                clockScaleTransition.setFromX(0.8);
                clockScaleTransition.setFromY(0.8);
                clockScaleTransition.setToX(1);
                clockScaleTransition.setToY(1);
                clockScaleTransition.setAutoReverse(true);
                clockScaleTransition.play();


            }
        } else {
            if(gameModel.cellControllers[i][j].cellModel.isFlag()){
                gameModel.remainMines ++;
            }else{
                gameModel.remainMines --;
            }
            gameModel.cellControllers[i][j].cellModel.setFlag();

            gameView.remainMinesLabel.setText(gameModel.remainMines+"/"+gameModel.minesTotalNumber);
        }
        gameModel.checkWin(gameModel.starNumber, gameModel.spentTime);
        gameModel.cellControllers[i][j].init();
    }

    //Flood-fill
    public void openCell(int i, int j) {
        gameModel.cellControllers[i][j].cellModel.setOpen(true);
        gameModel.openedCellsNum ++;
        gameView.openedCellsNumLabel.setText(gameModel.openedCellsNum +"/"+ N*M);

        if (gameModel.cellControllers[i][j].cellModel.isStar()) {
            gameModel.cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    gameView.starRotateTransition.play();

                    gameModel.cellControllers[i][j].cellModel.removeStar();
                    gameModel.cellControllers[i][j].init();

                    gameModel.starNumber++;
                    gameView.starNumberLabel.setText(Integer.toString(gameModel.starNumber));

                }
            });

            gameModel.cellControllers[i][j].cellView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    gameView.starRotateTransition.stop();
                }
            });
        }
        if (gameModel.cellControllers[i][j].cellModel.getNeighborMinesNum() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++) {
                for (int jj = j - 1; jj <= j + 1; jj++) {
                    if (gameModel.isInMinefield(ii, jj) &&
                            !gameModel.cellControllers[ii][jj].cellModel.isOpen() &&
                            !gameModel.cellControllers[ii][jj].cellModel.isMine()) {
                        openCell(ii, jj);
                        gameModel.cellControllers[ii][jj].init();
                    }
                }
            }
        }
    }

    public void setTimer() {
        try {
            gameModel.timer = new Timer();
            gameModel.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameView.remainTimeLabel.setText(gameModel.remainTime + "s");
                        }
                    });
                    gameModel.spentTime++;
                    gameModel.remainTime--;
                    if(gameModel.remainTime < 20){
                        gameView.remainTimeLabel.setTextFill(Color.RED);

                    }else{
                        gameView.remainTimeLabel.setTextFill(Color.BLACK);
                    }
                    if (gameModel.remainTime == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Time up");
                                gameModel.loseGame(
                                        gameModel.starNumber,
                                        gameModel.spentTime,
                                        gameModel.openedCellsNum,
                                        "Time is up!");
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

}
