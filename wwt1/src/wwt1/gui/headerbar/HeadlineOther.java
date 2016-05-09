package wwt1.gui.headerbar;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import wwt1.gui.interfaces.HeaderElement;

class HeadlineOther extends Text implements HeaderElement
{
    HeadlineOther(String text)
    {
        super( text );
        //setAlignment( Pos.TOP_LEFT );
        setFont( Font.font( "Tahoma", FontWeight.NORMAL, 10 ) );
    }

    @Override
    public Node getNode()
    {
        return this;
    }
}

