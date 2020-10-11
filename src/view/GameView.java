package view;

import controller.CellController;
import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.CellModel;

import java.util.Timer;

/**
 * Created by Tingying He on 2020/10/11.
 */
public class GameView extends BorderPane {
//    BorderPane gamePane= new BorderPane();
    VBox statusPane = new VBox();
    VBox btnPane = new VBox();
    public GridPane gridPane = new GridPane();

    //time
    AnchorPane timePane = new AnchorPane();
    public Timer timer;
    public Label timeLabel = new Label();
    public int remainTime = 10;
    Image clockIconImg = new Image(CellModel.clockImgURL);
    ImageView clockIconImgView = new ImageView(clockIconImg);
    public Label clockIntroLabel = new Label("Remaining Time");

    //number of opened cells
    AnchorPane openedCellsPane = new AnchorPane();
    public int openedCellsNumber = 0;
    public Label openedCellsNumberLabel = new Label();
    Image openedCellsIconImg = new Image("/img/1.png");
    ImageView openedCellsImgView = new ImageView(openedCellsIconImg);
    public Label openedCellsIntroLabel = new Label("Opened Cells");

    //star
    AnchorPane starPane = new AnchorPane();
    public int starNumber = 0;
    public Label starNumberLabel = new Label();
    Image starIconImg = new Image(CellModel.starImgURL);
    public ImageView starIconImgView = new ImageView(starIconImg);
    public Label starIntroLabel = new Label("Your Stars");

    //remain mines
    AnchorPane minePane = new AnchorPane();
    public int minesTotalNumber = 0;
    public int remainMines = 0;
    public Label remainMinesLabel = new Label();
    Image mineIconImg = new Image(CellModel.flagImgURL);
    ImageView mineIconImgView = new ImageView(mineIconImg);
    public Label mineIntroLabel = new Label("Hidden Mines");

    //tutorial

    //    AnchorPane tutorialPane = new AnchorPane();
    Image tutorialImg = new Image("/img/tutorial.png");
    ImageView tutorialImgView = new ImageView(tutorialImg);

    //Title
    AnchorPane titlePane = new AnchorPane();
    Label titleLabel = new Label("Minesweeper");
    //restart
    public Button restartBtn = new Button("New Game");
    public Button exitBtn = new Button("Exit");


    GameController gameController;

    public GameView(GameController gameController){
        this.gameController = gameController;
        this.setTop(titlePane);
        this.setCenter(gridPane);
        this.setRight(statusPane);

        this.setPadding(new Insets(20,30,20,30));

        gridPane.setPadding(new Insets(10,0,0,0));


        statusPane.getChildren().addAll(starPane,timePane,minePane,openedCellsPane,btnPane);
//        statusPane.setAlignment(Pos.CENTER);
        statusPane.setSpacing(3);

        btnPane.getChildren().addAll(restartBtn,exitBtn);
        btnPane.setAlignment(Pos.CENTER);
        btnPane.setSpacing(10);
        btnPane.setPadding(new Insets(100,0,0,0));


        restartBtn.setPrefSize(150,35);
        restartBtn.setStyle("-fx-background-color:#508EAC");
        restartBtn.setTextFill(Color.WHITE);

        exitBtn.setPrefSize(150,35);
        exitBtn.setStyle("-fx-background-color:#508EAC");
        exitBtn.setTextFill(Color.WHITE);




        //starPane
//        starPane.setStyle("-fx-background-color: YELLOW;");
//        starPane.setPrefSize(32, 50);
        starIntroLabel.setLayoutX(80);
        starIntroLabel.setLayoutY(10);
        //starPane text
        starNumberLabel.setFont(new Font("Arial", 30));
        starNumberLabel.setLayoutX(80);
        starNumberLabel.setLayoutY(30);
        //starpane animation
        starIconImgView.setFitHeight(50);
        starIconImgView.setFitWidth(50);
        starIconImgView.setX(10);
        starIconImgView.setY(10);

        starPane.getChildren().addAll(starIntroLabel,starNumberLabel,starIconImgView);

        //timePane
//        timePane.setStyle("-fx-background-color: Red;");
//        timePane.setPrefSize(32, 50);
        clockIntroLabel.setLayoutX(80);
        clockIntroLabel.setLayoutY(10);
        //timePane text
        timeLabel.setFont(new Font("Arial", 30));
        timeLabel.setLayoutX(80);
        timeLabel.setLayoutY(30);
        //timePane animation
        clockIconImgView.setFitHeight(50);
        clockIconImgView.setFitWidth(50);
        clockIconImgView.setX(10);
        clockIconImgView.setY(10);

        timePane.getChildren().addAll(timeLabel,clockIconImgView,clockIntroLabel);

        //openedCellsPane
//        openedCellsPane.setStyle("-fx-background-color: LightBlue;");
//        openedCellsPane.setPrefSize(32, 50);
        openedCellsIntroLabel.setLayoutX(80);
        openedCellsIntroLabel.setLayoutY(10);
        //openedCellsPane text
        openedCellsNumberLabel.setFont(new Font("Arial", 30));
        openedCellsNumberLabel.setLayoutX(80);
        openedCellsNumberLabel.setLayoutY(30);
//        openedCellsPane animation
        openedCellsImgView.setFitHeight(50);
        openedCellsImgView.setFitWidth(50);
        openedCellsImgView.setX(10);
        openedCellsImgView.setY(10);

        openedCellsPane.getChildren().addAll(openedCellsImgView,openedCellsNumberLabel,openedCellsIntroLabel);

        //remain mines


        //minePane
//        minePane.setStyle("-fx-background-color: LightGreen;");
//        starPane.setPrefSize(32, 50);
        mineIntroLabel.setLayoutX(80);
        mineIntroLabel.setLayoutY(10);
        //minePane text
        remainMinesLabel.setFont(new Font("Arial", 30));
        remainMinesLabel.setLayoutX(80);
        remainMinesLabel.setLayoutY(30);
        //minepane animation
        mineIconImgView.setFitHeight(50);
        mineIconImgView.setFitWidth(50);
        mineIconImgView.setX(10);
        mineIconImgView.setY(10);

        minePane.getChildren().addAll(mineIntroLabel,remainMinesLabel, mineIconImgView);

        //title
        titleLabel.setFont(new Font("Arial", 30) );

        //tutorial
        tutorialImgView.setLayoutX(0);
        tutorialImgView.setLayoutY(40);
        tutorialImgView.setFitWidth(428*0.85);
        tutorialImgView.setFitHeight(60*0.85);


        titlePane.getChildren().addAll(titleLabel,tutorialImgView);
    }

    public void init(int N, int M){
        gameController.gameModel.cellControllers = new CellController[N][M];

        timer.cancel();
        remainTime = 200;
        gameController.gameModel.setTimer();

        starNumber = 0;

        starNumberLabel.setText(Integer.toString(starNumber));

        openedCellsNumber = 0;

        openedCellsNumberLabel.setText( openedCellsNumber +"/"+ N*M);


        createCellsGrid();
        gameController.gameModel.NeighborMinesNumbers();
        gameController.gameModel.putStars();
        gameController.gameModel.putClocks();
        gameController.gameModel.addEventHandler();

        minesTotalNumber = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (gameController.gameModel.cellControllers[i][j].cellModel.isMine()) {
                    minesTotalNumber++;
                }
            }
        }
        remainMines = minesTotalNumber;
        remainMinesLabel.setText(remainMines+"/"+minesTotalNumber);
    }
    private void createCellsGrid() {
        for (int i = 0; i < gameController.N; i++)
            for (int j = 0; j < gameController.M; j++) {
                gameController.gameModel.cellControllers[i][j] = new CellController();
                gameController.gameView.gridPane.add(gameController.gameModel.cellControllers[i][j].cellView, i, j, 1, 1);
            }
    }
}
