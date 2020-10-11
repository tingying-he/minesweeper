package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.GameModel;
import view.GameView;

/**
 * Created by Tingying He on 2020/10/11.
 */
public class GameController {
    public int N, M;
    public GameView gameView = new GameView(this);
    public GameModel gameModel = new GameModel(this);

    public GameController(int N, int M){
        this.N = N;
        this.M = M;

        gameModel.setTimer();

        gameView.init(N,M);

        //addEventHandler to buttons on gamePane
        gameView.restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            gameModel.restart();
        });
        gameView.exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Platform.exit();
            System.exit(0);
        });

//        addEventHandler();
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
    public void updateModel(int i, int j, boolean left) {
        if (left) {
            gameModel.openCell(i, j);
            if (gameModel.cellControllers[i][j].cellModel.isMine()) {
//                System.out.println("Game Over");
                gameModel.loseGame("You click a mine");
            }

            if (gameModel.cellControllers[i][j].cellModel.isClock()) {
                gameView.timer.cancel();
                gameView.remainTime = gameView.remainTime + 30;
                gameModel.setTimer();
                gameModel.cellControllers[i][j].cellModel.removeClock();

            }
        } else {
            gameModel.cellControllers[i][j].cellModel.setFlag();
            gameView.remainMines --;
            gameView.remainMinesLabel.setText(gameView.remainMines+"/"+gameView.minesTotalNumber);
        }
        gameModel.checkWin();
        gameModel.cellControllers[i][j].cellView.init(gameModel.cellControllers[i][j]);
    }


}
