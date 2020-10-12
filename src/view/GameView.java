package view;

import controller.CellController;
import controller.GameController;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.CellModel;

import java.util.Timer;

/**
 * Created by Tingying He on 2020/10/11.
 */
public class GameView extends BorderPane {

    private GameController gameController;

    private VBox statusPane = new VBox();
    private VBox btnPane = new VBox();
    public GridPane gridPane = new GridPane();

    //remaining time status pane
    private AnchorPane timePane = new AnchorPane();
    public Label remainTimeLabel = new Label(); //show remain time
    private Image clockIconImg = new Image(CellModel.clockImgURL);
    public ImageView clockIconImgView = new ImageView(clockIconImg);
    public Label clockIntroLabel = new Label("Remaining Time");

    //number of opened cells status pane
    private AnchorPane openedCellsPane = new AnchorPane();
    public Label openedCellsNumLabel = new Label();
    private Image openedCellsIconImg = new Image("img/1.png");
    private ImageView openedCellsImgView = new ImageView(openedCellsIconImg);
    public Label openedCellsIntroLabel = new Label("Opened/All Cells");

    //star status pane
    private AnchorPane starPane = new AnchorPane();
    public Label starNumberLabel = new Label();
    Image starIconImg = new Image(CellModel.starImgURL);
    public ImageView starIconImgView = new ImageView(starIconImg);
    public Label starIntroLabel = new Label("Your Stars");

    //remaining mines status pane
    private AnchorPane minePane = new AnchorPane();
    public Label remainMinesLabel = new Label();
    Image mineIconImg = new Image(CellModel.flagImgURL);
    ImageView mineIconImgView = new ImageView(mineIconImg);
    public Label mineIntroLabel = new Label("Flagged/All Mines");

    //title pane
    private AnchorPane titlePane = new AnchorPane();
    private Label titleLabel = new Label("Minesweeper");
    //tutorial
    private Image tutorialImg = new Image("img/tutorial.png");
    private ImageView tutorialImgView = new ImageView(tutorialImg);

    //restart
//    public Button customizeBtn = new Button("Customize Game");
    public Button restartBtn = new Button("New Game");
    public Button exitBtn = new Button("Exit");



    public RotateTransition starRotateTransition;

    public GameView(GameController gameController){
        this.gameController = gameController;

        //game pane
        this.setTop(titlePane);
        this.setCenter(gridPane);
        this.setRight(statusPane);

        this.setPadding(new Insets(20,30,20,30));

        //TitlePane
        titleLabel.setFont(new Font("Arial", 30) );
        //tutorial
        tutorialImgView.setLayoutX(0);
        tutorialImgView.setLayoutY(40);
        tutorialImgView.setFitWidth(428*0.85);
        tutorialImgView.setFitHeight(60*0.85);

        titlePane.getChildren().addAll(titleLabel,tutorialImgView);

        //gridPane(minefield)
        gridPane.setPadding(new Insets(10,0,0,0));


        //statusPane
        statusPane.getChildren().addAll(starPane,timePane,minePane,openedCellsPane,btnPane);
        statusPane.setSpacing(3);

        //starPane
        starIntroLabel.setLayoutX(80);
        starIntroLabel.setLayoutY(10);
        //star pane text
        starNumberLabel.setFont(new Font("Arial", 30));
        starNumberLabel.setLayoutX(80);
        starNumberLabel.setLayoutY(30);
        //star pane animation
        starIconImgView.setFitHeight(50);
        starIconImgView.setFitWidth(50);
        starIconImgView.setX(10);
        starIconImgView.setY(10);

        starPane.getChildren().addAll(starIntroLabel,starNumberLabel,starIconImgView);

        //rotate animation for star on statusPane
        starRotateTransition = new RotateTransition(Duration.millis(100), starIconImgView);
        starRotateTransition.setFromAngle(-30);
        starRotateTransition.setToAngle(30);
        starRotateTransition.setCycleCount(Timeline.INDEFINITE);
        starRotateTransition.setAutoReverse(true);

        //timePane
        clockIntroLabel.setLayoutX(80);
        clockIntroLabel.setLayoutY(10);
        //timePane text
        remainTimeLabel.setFont(new Font("Arial", 30));
        remainTimeLabel.setLayoutX(80);
        remainTimeLabel.setLayoutY(30);
        //timePane animation
        clockIconImgView.setFitHeight(50);
        clockIconImgView.setFitWidth(50);
        clockIconImgView.setX(10);
        clockIconImgView.setY(10);

        timePane.getChildren().addAll(remainTimeLabel,clockIconImgView,clockIntroLabel);

        //openedCellsPane
        openedCellsIntroLabel.setLayoutX(80);
        openedCellsIntroLabel.setLayoutY(10);
        //openedCellsPane text
        openedCellsNumLabel.setFont(new Font("Arial", 30));
        openedCellsNumLabel.setLayoutX(80);
        openedCellsNumLabel.setLayoutY(30);
//        openedCellsPane animation
        openedCellsImgView.setFitHeight(50);
        openedCellsImgView.setFitWidth(50);
        openedCellsImgView.setX(10);
        openedCellsImgView.setY(10);

        openedCellsPane.getChildren().addAll(openedCellsImgView,openedCellsNumLabel,openedCellsIntroLabel);


        //minePane
        mineIntroLabel.setLayoutX(80);
        mineIntroLabel.setLayoutY(10);
        //minePane text
        remainMinesLabel.setFont(new Font("Arial", 30));
        remainMinesLabel.setLayoutX(80);
        remainMinesLabel.setLayoutY(30);
        //minePane animation
        mineIconImgView.setFitHeight(50);
        mineIconImgView.setFitWidth(50);
        mineIconImgView.setX(10);
        mineIconImgView.setY(10);

        minePane.getChildren().addAll(mineIntroLabel,remainMinesLabel, mineIconImgView);

        //btnPane
        btnPane.getChildren().addAll(restartBtn,exitBtn);
        btnPane.setAlignment(Pos.CENTER);
        btnPane.setSpacing(10);
        btnPane.setPadding(new Insets(160,0,0,0));
        //customize Button
//        customizeBtn.setPrefSize(150,35);
//        customizeBtn.setStyle("-fx-background-color:#508EAC");
//        customizeBtn.setTextFill(Color.WHITE);
        //restart button
        restartBtn.setPrefSize(150,35);
        restartBtn.setStyle("-fx-background-color:#508EAC");
        restartBtn.setTextFill(Color.WHITE);
        //exit button
        exitBtn.setPrefSize(150,35);
        exitBtn.setStyle("-fx-background-color:#508EAC");
        exitBtn.setTextFill(Color.WHITE);
    }

}
