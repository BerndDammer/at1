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

public class InstallPane extends GridPane implements FunctionPane
{
    
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton ()
        {
            setText( "Mo more plug in" );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println( "Fummel Bummel" );
        }
    }

    private final Logger logger = LoggingInit.get( this );

    private final RootParent rootParent;

    public InstallPane(RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Install", this, rootParent) );
        
        setPadding( new Insets( 10, 10, 10, 10 ) );
        add( new MyButton(), 0, 1 );

    }
    @Override
    public void setActive( boolean active )
    {
        logger.info( "Install switched" );
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Install" );
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
