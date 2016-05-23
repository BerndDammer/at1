package as.gui.functionpanes;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sound.midi.Transmitter;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.interim.message.LC_MessageOfferer;
import as.interim.message.MessageBase;
import as.interim.message.MessageClamp;
import as.interim.message.MessagePlatformSelect;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SelectPane extends GridPane implements IC_FunctionPane, Runnable
{
    private final Logger logger = LoggingInit.get( this );

    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton()
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

    class PlatformButton extends ToggleButton implements EventHandler<ActionEvent>
    {
        PlatformButton( String platformName )
        {
            setText( platformName );
            setOnAction( this );
        }

        @Override
        public void handle( ActionEvent event )
        {
            //super.handle( event );
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.SET;
            transmittPlatformSelect.selected = getText();
            StaticStarter.getClientPort().publish( transmittPlatformSelect );
        }
    }

    // private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;
    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private final LC_MessageOfferer messageClamp;
    private List<ToggleButton> platformButtons;
    private ToggleGroup platformGroup;
    private VBox platformPanel;

    public SelectPane( IC_RootParent rootParent )
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton( "Select", this, rootParent ) );

        setPadding( new Insets( 5, 5, 5, 5 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );

        add( new MyButton(), 1, 1 );
        MessageClamp messageClamp = new MessageClamp( this );
        this.messageClamp = messageClamp;
        StaticStarter.getClientPort().register( receivePlatformSelect, messageClamp );
    }

    @Override
    public void setActive( boolean active )
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle( "Select" );
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.REQUEST_LIST;
            StaticStarter.getClientPort().publish( transmittPlatformSelect );
            // StaticStarter.getClientPort().publish( new MessageBase() );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }

    void handleMessage( MessagePlatformSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                platformPanel = new VBox();
                platformGroup = new ToggleGroup();
                platformButtons = new LinkedList<>();
                for (String pn : mps.names)
                {
                    PlatformButton tb = new PlatformButton( pn );
                    platformButtons.add( tb );
                    platformGroup.getToggles().add( tb );
                    platformPanel.getChildren().add( tb );
                }
                add( platformPanel, 0, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }

    @Override
    public void run()
    {
        if (StaticConst.LOG_INTERIM)
            logger.info( "Select Pane gots Message" );
        MessageBase mb = messageClamp.aquire();
        if (mb instanceof MessagePlatformSelect)
        {
            MessagePlatformSelect mps = (MessagePlatformSelect) mb;
            handleMessage( mps );
        }
        else
        {
            logger.warning( "unexpected message type" );
        }
        messageClamp.release();
    }
}
