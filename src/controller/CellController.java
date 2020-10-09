package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.CellModel;
import view.CellView;


/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellController{

    public int x,y;
    public CellController(int x, int y ){
        this.x = x;
        this.y = y;
    }

    public CellModel cellModel = new CellModel(x, y);
    public CellView cellView = new CellView(cellModel);


}
