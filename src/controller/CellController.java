package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.CellModel;
import view.CellView;

import java.util.ArrayList;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellController{
    private int x,y;
    public CellController(int x, int y ){
        this.x = x;
        this.y = y;
        cellView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    setModel(true);
                }
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    setModel(false);
                }
            }
        });

    }

    public CellModel cellModel = new CellModel(x,y);
    public CellView cellView = new CellView(cellModel);

    private void setModel(boolean isLeftClicked){
        if(isLeftClicked){
            cellModel.setOpen();
        }
        else{
            cellModel.setFlag();
        }
        cellView.init(cellModel);
    }

}
