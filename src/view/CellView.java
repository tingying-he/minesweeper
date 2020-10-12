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
                this.getChildren().add(drawImg(
                        cellController.cellModel.cellSide,
                        cellController.cellModel.cellSide,
                        CellModel.mineImgURL)
                );
            } else if (cellController.cellModel.isStar()) {
                if(!cellController.cellModel.hasPutStar) {
                    ImageView starImgView;
                    starImgView = drawImg(cellController.cellModel.cellSide, cellController.cellModel.cellSide, CellModel.starImgURL);
                    this.getChildren().add(starImgView);

                    //rotate animation for stars in cells
                    RotateTransition starRotateTransition =
                            new RotateTransition(Duration.millis(3000), starImgView);
                    starRotateTransition.setFromAngle(-30);
                    starRotateTransition.setToAngle(30);
                    starRotateTransition.setCycleCount(Timeline.INDEFINITE);
                    starRotateTransition.setAutoReverse(true);
                    starRotateTransition.play();
                    cellController.cellModel.hasPutStar = true;
                }

            } else if (cellController.cellModel.isClock()) {
                ImageView clockImgView;
                clockImgView = drawImg(
                        cellController.cellModel.cellSide,
                        cellController.cellModel.cellSide,
                        CellModel.clockImgURL);
                this.getChildren().add(clockImgView);

                //scale animation for stars in cells
                ScaleTransition clockScaleTransition =
                        new ScaleTransition(Duration.millis(1000), clockImgView);
                clockScaleTransition.setFromX(1);
                clockScaleTransition.setFromY(1);
                clockScaleTransition.setToX(0.8);
                clockScaleTransition.setToY(0.8);
                clockScaleTransition.setCycleCount(Timeline.INDEFINITE);
                clockScaleTransition.setAutoReverse(true);
                clockScaleTransition.play();

            } else {
                this.getChildren().add(drawImg(
                        cellController.cellModel.cellSide,
                        cellController.cellModel.cellSide,
                        CellModel.numberImgURL(cellController.cellModel.getNeighborMinesNum())));
            }
        } else {
            //if the cell isn't opened
                if (cellController.cellModel.isFlag()) {
                    this.getChildren().add(drawImg(
                            cellController.cellModel.cellSide,
                            cellController.cellModel.cellSide,
                            CellModel.flagImgURL));
                } else {
                    //if the cell isn't opened or flagged
                    this.getChildren().add(drawImg(
                            cellController.cellModel.cellSide,
                            cellController.cellModel.cellSide,
                            CellModel.coverImgURL));
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

