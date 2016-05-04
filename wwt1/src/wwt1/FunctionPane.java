package wwt1;

import javafx.scene.layout.Pane;

public interface FunctionPane
{
    Pane getPane();
    static void none()
    {
        int a = StaticConst.GAP;
    };
}
