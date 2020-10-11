package controller;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.CellModel;
import model.GridModel;
import view.CellView;
import view.GridView;


import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class GridController {
    private int N, M;
    BorderPane gamePane= new BorderPane();
    VBox statusPane = new VBox();
    HBox btnPane = new HBox();

    GridPane gridPane = new GridPane();

    //time
    AnchorPane timePane = new AnchorPane();
    public Timer timer;
    public Label timeLabel = new Label();
    private int remainTime = 10;
    Image clockIconImg = new Image(CellModel.clockImgURL);
    ImageView clockIconImgView = new ImageView(clockIconImg);
    public Label clockIntroLabel = new Label("Remaining Time");

    //number of opened cells
    AnchorPane openedCellsPane = new AnchorPane();
    private int openedCellsNumber = 0;
    public Label openedCellsNumberLabel = new Label();
    Image openedCellsIconImg = new Image(CellModel.blockImgURL);
    ImageView openedCellsImgView = new ImageView(openedCellsIconImg);
    public Label openedCellsIntroLabel = new Label("Opened Cells");

    //star
    AnchorPane starPane = new AnchorPane();
    public int starNumber = 0;
    public Label starNumberLabel = new Label();
    Image starIconImg = new Image(CellModel.starImgURL);
    ImageView starIconImgView = new ImageView(starIconImg);
    public Label starIntroLabel = new Label("Your Stars");

    //remain mines
    AnchorPane minePane = new AnchorPane();
    public int minesTotalNumber = 0;
    public int remainMines = 0;
    public Label remainMinesLabel = new Label();
    Image mineIconImg = new Image(CellModel.mineImgURL);
    ImageView mineIconImgView = new ImageView(mineIconImg);
    public Label mineIntroLabel = new Label("Hidden Mines");

    //tutorial

    AnchorPane tutorialPane = new AnchorPane();
    public Label tutorialLabel = new Label("drag STARS to collect, click CLOCKS to gain time");



    //restart
    public Button restartBtn = new Button("New Game");
    public Button exitBtn = new Button("Exit");


    public CellController[][] cellControllers;

    public GridController(int N, int M){
        this.N = N;
        this.M = M;


//        gamePane.getChildren().addAll(gridPane,statusPane);
//        gamePane.setAlignment(Pos.CENTER);
//        gamePane.setSpacing(20);
        gamePane.setTop(tutorialPane);
        gamePane.setCenter(gridPane);
        gamePane.setRight(statusPane);
        gamePane.setBottom(btnPane);

        gamePane.setPadding(new Insets(20,30,20,30));


        statusPane.getChildren().addAll(starPane,timePane,minePane,openedCellsPane);
//        statusPane.setAlignment(Pos.CENTER);
        btnPane.getChildren().addAll(restartBtn,exitBtn);
        btnPane.setAlignment(Pos.CENTER_RIGHT);
        btnPane.setSpacing(20);



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


        //tutorial
        tutorialLabel.setLayoutX(10);
        tutorialLabel.setLayoutY(10);
        tutorialPane.getChildren().addAll(tutorialLabel);


        setTimer();
        init(N,M);
        restartBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
//            init(N,M);
            restart();
        });

        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            Platform.exit();
            System.exit(0);
        });











    }
    public void init(int N, int M) {






        this.cellControllers = new CellController[N][M];

        timer.cancel();
        remainTime = 10;
        setTimer();

        starNumber = 0;

        starNumberLabel.setText(Integer.toString(starNumber));

        openedCellsNumber = 0;

        openedCellsNumberLabel.setText( openedCellsNumber +"/"+ N*M);


        createCellsGrid();
        NeighborMinesNumbers();
        addEventHandler();

        minesTotalNumber = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (cellControllers[i][j].cellModel.isMine()) {
                    minesTotalNumber++;
                }
            }
        }
        remainMines = minesTotalNumber;
        remainMinesLabel.setText(remainMines+"/"+minesTotalNumber);



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
                            if (ii >= 0 && ii < N && jj >= 0 && jj < M && cellControllers[ii][jj].cellModel.isMine())
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
                loseGame("You click a mine");
            }
            if (cellControllers[i][j].cellModel.isStar()) {
                System.out.println("Star!");
//                ScaleTransition st = new ScaleTransition(Duration.millis(300), starIconImgView);

                RotateTransition starRotateTransition =
                        new RotateTransition(Duration.millis(100), starIconImgView);
                starRotateTransition.setFromAngle(-30);
                starRotateTransition.setToAngle(30);
                starRotateTransition.setCycleCount(Timeline.INDEFINITE);
                starRotateTransition.setAutoReverse(true);
//                starRotateTransition.play();

                cellControllers[i][j].cellView.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("Drag detected");
                        starRotateTransition.play();
                        cellControllers[i][j].cellModel.moveStar();
                        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
                        starNumber++;
                        starNumberLabel.setText(Integer.toString(starNumber));

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
                remainTime = remainTime + 30;
                timer.cancel();
                setTimer();
                cellControllers[i][j].cellModel.moveClock();

            }
        } else {
            cellControllers[i][j].cellModel.setFlag();
            remainMines --;
            remainMinesLabel.setText(remainMines+"/"+minesTotalNumber);
        }
        winGame();
        cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
    }

    //Flood-fill
    public void openCell(int i, int j) {
        cellControllers[i][j].cellModel.setOpen();
//        System.out.println(cellControllers[i][j].cellModel.getNumbers());
        openedCellsNumber ++;
        openedCellsNumberLabel.setText(openedCellsNumber +"/"+ N*M);

        if (cellControllers[i][j].cellModel.getNumbers() == 0) {
            for (int ii = i - 1; ii <= i + 1; ii++)
                for (int jj = j - 1; jj <= j + 1; jj++)
                    if (ii >= 0 && ii < N && jj >= 0 && jj < M && !cellControllers[ii][jj].cellModel.open && !cellControllers[ii][jj].cellModel.isMine()) {
                        openCell(ii, jj);
                        cellControllers[ii][jj].cellView.init(cellControllers[ii][jj].cellModel);
//            System.out.println(cellControllers[i][j].cellModel.getNumbers());
                    }
        }
    }

    public void setTimer() {
       try {
           timer = new Timer();
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           timeLabel.setText(remainTime + "s");

                       }
                   });
                   remainTime--;
                   if (remainTime == 0) {
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
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                cellControllers[i][j].cellModel.setOpen();
                cellControllers[i][j].cellView.init(cellControllers[i][j].cellModel);
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
        timer.cancel();
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

    public void restart(){
        init(N,M);
    }
}



