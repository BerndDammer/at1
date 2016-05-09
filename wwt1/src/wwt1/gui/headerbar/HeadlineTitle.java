package wwt1.gui.headerbar;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import wwt1.gui.interfaces.HeaderElement;

public class HeadlineTitle extends Text implements HeaderElement
{
    public HeadlineTitle()
    {
        super( "Welcome" );
        //setAlignment( Pos.TOP_LEFT );
        setFont( Font.font( "Tahoma", FontWeight.NORMAL, 15 ) );
    }

    @Override
    public Node getNode()
    {
        return this;
    }
}
