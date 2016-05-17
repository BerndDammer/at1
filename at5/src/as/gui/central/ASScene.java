package as.gui.central;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

class ASScene extends Scene
{
    ASScene( int w, int h )
    {
        super( new RootNode(), w, h, Color.ALICEBLUE );
    }

    ASScene()
    {
        super( new RootNode() );
        
    }
}


