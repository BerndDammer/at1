package wwt1;

import javafx.scene.layout.HBox;

public class SelectionBar extends HBox
{
    public SelectionBar(RootGrid parent)
    {
        // TODO Auto-generated constructor stub
    }

    public void add(SelectionButton button)
    {
        getChildren().add( button );
    }
}
