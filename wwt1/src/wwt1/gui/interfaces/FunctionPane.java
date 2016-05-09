package wwt1.gui.interfaces;

import javafx.scene.layout.Pane;
import wwt1.gui.central.StaticConst;

public interface FunctionPane
{
    Pane getPane();
    static void none()
    {
        int a = StaticConst.GU;
    };
    void setActive( boolean active);
}
