package view;

import controller.CellController;
import javafx.animation.RotateTransition;
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
    private CellController cellController;

    public CellView(CellController cellController){
        init(cellController);
    }

    public void init(CellController cellController) {
        this.cellController = cellController;

        if (cellController.cellModel.isOpen()) {
            if (cellController.cellModel.isMine()) {
                Image img = new Image(CellModel.mineImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellController.cellModel.cellSide);
                imgView.setFitWidth(cellController.cellModel.cellSide);
                this.getChildren().add(imgView);
            } else if (cellController.cellModel.isStar()) {
                Image img = new Image(CellModel.starImgURL);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(cellController.cellModel.cellSide);
                imgView.setFitWidth(cellController.cellModel.cellSide);
                this.getChildren().add(imgView);
                RotateTransition starRotateTransition =
                        new RotateTransition(Duration.millis(3000), imgView);
                starRotateTransition.setFromAngle(-30);
                starRotateTransition.setToAngle(30);
                starRotateTransition.setCycleCount(Timeline.INDEFINITE);
                starRotateTransition.setAutoReverse(true);
                starRotateTransition.play();


            } else if (cellController.cellModel.isClock()) {
                ImageView clockImgView;
                clockImgView = drawImg(cellController.cellModel.cellSide, cellController.cellModel.cellSide, CellModel.clockImgURL);
                this.getChildren().add(clockImgView);
                ScaleTransition clockScaleTransition = new ScaleTransition(Duration.millis(1000), clockImgView);
                clockScaleTransition.setFromX(1); // original x
                clockScaleTransition.setFromY(1); // original y
                clockScaleTransition.setToX(0.8); // final x is 25 times the original
                clockScaleTransition.setToY(0.8); // final y is 25 times the original
                clockScaleTransition.setCycleCount(Timeline.INDEFINITE);
                clockScaleTransition.setAutoReverse(true);
                clockScaleTransition.play();
            } else {
                this.getChildren().add(drawImg(cellController.cellModel.cellSide, cellController.cellModel.cellSide, CellModel.numberImgURL(cellController.cellModel.getNeighborMinesNum())));
            }
        } else {
                if (cellController.cellModel.isFlag()) {
                    this.getChildren().add(drawImg(cellController.cellModel.cellSide, cellController.cellModel.cellSide, CellModel.flagImgURL));
                } else {
                    this.getChildren().add(drawImg(cellController.cellModel.cellSide, cellController.cellModel.cellSide, CellModel.coverImgURL));
                }

        }
    }

    private ImageView drawImg(int width,int height, String imgURL){
        Image img = new Image(imgURL);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(height);
        imgView.setFitWidth(width);
        return imgView;
    }
}

