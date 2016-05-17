package as.gui.functionpanes;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SelectPane extends GridPane implements IC_FunctionPane
{
    
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton ()
        {
            setText( "What do You want" );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println( "No Meaning" );
        }
    }

    //private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;

    public SelectPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Select", this, rootParent) );
        
        setGridLinesVisible( true );
        setPadding( new Insets( 25, 25, 25, 25 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );


        add( new MyButton(), 0, 3 );

    }
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Select" );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }

}
