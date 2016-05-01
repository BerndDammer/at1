package wwt1;

import javafx.scene.layout.FlowPane;

public class HeaderBar extends FlowPane
{
    public HeaderBar(RootGrid parent)
    {
    }

    public void add( HeaderElement he)
    {
        getChildren().add( he );
    }
    public void setTitle(String title)
    {
        
    }
}
