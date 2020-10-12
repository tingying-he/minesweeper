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
        init(N,M);
        
        gameView.restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            gameModel.restart();
        });
        gameView.exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Platform.exit();
            System.exit(0);
        });
    }

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

}
