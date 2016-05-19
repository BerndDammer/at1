package as.gui.functionpanes;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.interim.message.MessagePlatformSelect;
import as.starter.StaticStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SelectPane extends GridPane implements IC_FunctionPane, IL_Receiver
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
    private final MessagePlatformSelect receivePlatformSelect= new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect= new MessagePlatformSelect();

    public SelectPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Select", this, rootParent) );
        
        setGridLinesVisible( true );
        setPadding( new Insets( 25, 25, 25, 25 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );


        add( new MyButton(), 0, 3 );
        StaticStarter.getClientPort().register( receivePlatformSelect, this );
    }
    @Override
    public void setActive( boolean active )
    {
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "Select" );
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.GET_LIST;
            StaticStarter.getClientPort().publish(transmittPlatformSelect );
            StaticStarter.getClientPort().publish( new MessageBase() );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }
    @Override
    public void receive( MessageBase message )
    {
        
    }
}
