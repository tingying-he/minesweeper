package view;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.CellModel;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellView extends StackPane {
    private CellModel cellModel;

    public CellView(CellModel cellModel){
        init(cellModel);
    }

    public void init(CellModel cellModel) {
        this.cellModel = cellModel;
        setTranslateX(cellModel.x * cellModel.cellSide);
        setTranslateY(cellModel.y * cellModel.cellSide);

        if(cellModel.open) {
            if (cellModel.isMine()) {
                Image img = new Image(CellModel.mineImgURL);
                ImageView imgView = new ImageView(img);
                this.getChildren().add(imgView);
            } else if (cellModel.isStar()) {
                Image img = new Image(CellModel.starImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellModel.cellSide);
                imgView.setFitWidth(cellModel.cellSide);
                this.getChildren().add(imgView);


            } else if (cellModel.isClock()) {
                Image img = new Image(CellModel.clockImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellModel.cellSide);
                imgView.setFitWidth(cellModel.cellSide);
                this.getChildren().add(imgView);

            }
            else{
                Image img = new Image(CellModel.numberImgURL(cellModel.getNumbers()));
//                Image img = new Image("/img/0.png");
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellModel.cellSide);
                imgView.setFitWidth(cellModel.cellSide);
                this.getChildren().add(imgView);
//                System.out.println(cellModel.getNumbers());
            }
        }
        else{
            if(cellModel.flag){
                Image img = new Image(CellModel.flagImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellModel.cellSide);
                imgView.setFitWidth(cellModel.cellSide);
                this.getChildren().add(imgView);
            }
            else{
                Image img = new Image(CellModel.blockImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellModel.cellSide);
                imgView.setFitWidth(cellModel.cellSide);
                this.getChildren().add(imgView);
            }
        }
    }

}
