package controller;

import model.CellModel;
import view.CellView;


/**
 * Created by Tingying He on 2020/10/7.
 */
public class CellController{

    public CellController(){
    }

    public CellModel cellModel = new CellModel(this);
    public CellView cellView = new CellView(this);

}
