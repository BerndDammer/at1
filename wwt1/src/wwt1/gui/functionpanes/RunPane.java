package wwt1.gui.functionpanes;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logging.LoggingInit;
import wwt1.gui.interfaces.FunctionPane;
import wwt1.gui.interfaces.RootParent;
import wwt1.gui.selectionbar.HomeButton;
import wwt1.gui.selectionbar.SelectionButton;

public class RunPane extends GridPane implements FunctionPane
{
    
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton ()
        {
            setText( "Go go go" );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println( "running" );
        }
    }

    private final Logger logger = LoggingInit.get( this );

    private final RootParent rootParent;

    public RunPane(RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Run", this, rootParent) );
        
        setGridLinesVisible( true );
        setPadding( new Insets( 5, 5, 5, 5 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );

        add( new MyButton(), 0, 3 );

    }
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Run" );
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
