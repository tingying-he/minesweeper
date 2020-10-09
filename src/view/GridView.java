package view;

import controller.CellController;
import controller.GridController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Created by Tingying He on 2020/10/7.
 */
public class GridView extends GridPane {

    public GridView(GridController gridController) {
        for(int i = 0 ; i < 20 ; i ++)
            for(int j = 0 ; j < 20 ; j ++){
                this.add(gridController.cellControllers[i][j].cellView,i,j,1,1);
            }
    }
}
