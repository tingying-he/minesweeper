package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.CellModel;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellView extends StackPane {
    private CellModel cellModel;

    public CellView(CellModel cellModel) {
        this.cellModel = cellModel;
        setTranslateX(cellModel.x * cellModel.cellSide);
        setTranslateY(cellModel.y * cellModel.cellSide);

        if(cellModel.isMine()){
            Image img = new Image(CellModel.mineImgURL);
            ImageView imgView = new ImageView(img);
            this.getChildren().add(imgView);
        }
        else if(cellModel.isStar()){
            Image img = new Image(CellModel.starImgURL);
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(cellModel.cellSide);
            imgView.setFitWidth(cellModel.cellSide);
            this.getChildren().add(imgView);
        }
        else if(cellModel.isClock()){
            Image img = new Image(CellModel.clockImgURL);
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
