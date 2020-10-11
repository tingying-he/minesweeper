package controller;

import javafx.application.Platform;
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
        gameView.restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            gameModel.restart();
        });

        gameView.exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Platform.exit();
            System.exit(0);
        });

    }



}
