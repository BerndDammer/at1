package wwt1;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SelectionBar extends HBox
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
