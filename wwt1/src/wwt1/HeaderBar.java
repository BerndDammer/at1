package wwt1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HeaderBar extends FlowPane
{
    interface HeaderElement
    {
        Node getNode();
    }
    class HeadlineTitle extends Text implements HeaderElement
    {
        public HeadlineTitle ()
        {
            super( "Welcome" );
            setAlignment( Pos.TOP_LEFT );
            setFont( Font.font( "Tahoma", FontWeight.NORMAL, 15 ) );
        }

        @Override
        public Node getNode()
        {
            return this;
        }
    }

    public HeaderBar(RootGrid parent)
    {
        add( new HeadlineTitle() );
        setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void add( HeaderElement he)
    {
        getChildren().add( he.getNode() );
    }
    public void setTitle(String title)
    {
        
    }
}
