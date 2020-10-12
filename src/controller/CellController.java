package controller;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.CellModel;
import view.CellView;


/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellController{
    public CellModel cellModel = new CellModel(this);
    public CellView cellView = new CellView(this);

    public CellController(){
        init();
    }


    public void init() {

        if (cellModel.isOpen()) {
            if (cellModel.isMine()) {
                cellView.getChildren().add(drawImg(
                        cellModel.cellSide,
                        cellModel.cellSide,
                        CellModel.mineImgURL)
                );
            } else if (cellModel.isStar()) {
                if(!cellModel.hasPutStar) {
                    ImageView starImgView;
                    starImgView = drawImg(cellModel.cellSide, cellModel.cellSide, CellModel.starImgURL);
                    cellView.getChildren().add(starImgView);

                    //rotate animation for stars in cells
                    RotateTransition starRotateTransition =
                            new RotateTransition(Duration.millis(3000), starImgView);
                    starRotateTransition.setFromAngle(-30);
                    starRotateTransition.setToAngle(30);
                    starRotateTransition.setCycleCount(Timeline.INDEFINITE);
                    starRotateTransition.setAutoReverse(true);
                    starRotateTransition.play();
                    cellModel.hasPutStar = true;
                }

            } else if (cellModel.isClock()) {
                ImageView clockImgView;
                clockImgView = drawImg(
                        cellModel.cellSide,
                        cellModel.cellSide,
                        CellModel.clockImgURL);
                cellView.getChildren().add(clockImgView);

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
                cellView.getChildren().add(drawImg(
                        cellModel.cellSide,
                        cellModel.cellSide,
                        CellModel.numberImgURL(cellModel.getNeighborMinesNum())));
            }
        } else {
            //if the cell isn't opened
            if (cellModel.isFlag()) {
                cellView.getChildren().add(drawImg(
                        cellModel.cellSide,
                        cellModel.cellSide,
                        CellModel.flagImgURL));
            } else {
                //if the cell isn't opened or flagged
                cellView.getChildren().add(drawImg(
                        cellModel.cellSide,
                        cellModel.cellSide,
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
