package wwt1.gui.selectionbar;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import wwt1.gui.central.RootGrid;
import wwt1.gui.interfaces.SelectionInterface;

public class SelectionBar extends HBox implements SelectionInterface
{
    public SelectionBar(RootGrid parent)
    {
        setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void add(SelectionButton button)
    {
        getChildren().add( button );
    }
}
