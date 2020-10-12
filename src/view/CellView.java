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
        this.cellController = cellController;
    }
}

