package as.gui.functionpanes;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.MessageBase;
import as.interim.message.MessageChannelSelect;
import as.interim.message.MessagePlatformSelect;
import as.starter.LoggingInit;
import as.starter.StaticStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SelectPane extends GridPane implements IC_FunctionPane, IL_MessageBaseReceiver<MessageBase>, 
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
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.SET;
            transmittPlatformSelect.selected = getText();
            StaticStarter.getClientPort().publish( transmittPlatformSelect );
        }
    }

    // private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;
    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private List<ToggleButton> platformButtons;
    private ToggleGroup platformGroup;
    private Node platformPanel = null;
    private Node inputPanel=null;
    private Node outputPanel=null;

    public SelectPane( IC_RootParent rootParent )
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton( "Select", this, rootParent ) );

        setPadding( new Insets( 5, 5, 5, 5 ) );
        setVgap( 2.0 );
        setHgap( 1.0 );

        add( new MyButton(), 1, 1 );
        StaticStarter.getClientPort().register( receivePlatformSelect, this );
    }

    @Override
    public void setActive( boolean active )
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle( "Select" );
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.REQUEST_LIST;
            StaticStarter.getClientPort().publish( transmittPlatformSelect );
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
    public void receiveMessage( MessageBase mb )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                if(platformPanel != null)
                    getChildren().remove( platformPanel );
                platformPanel = new VBox();
                platformGroup = new ToggleGroup();
                platformButtons = new LinkedList<>();
                for (String pn : mps.names)
                {
                    PlatformButton tb = new PlatformButton( pn );
                    platformButtons.add( tb );
                    platformGroup.getToggles().add( tb );
                    platformPanel.getChildren().add( tb );
                    if( pn.equals( mps.selected ))
                        tb.setSelected( true );
                }
                // TODO remove old
                add( platformPanel, 0, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }
    //@Override
    public void receiveMessage( MessagePlatformSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                if(platformPanel != null)
                    getChildren().remove( platformPanel );
                platformPanel = new VBox();
                platformGroup = new ToggleGroup();
                platformButtons = new LinkedList<>();
                for (String pn : mps.names)
                {
                    PlatformButton tb = new PlatformButton( pn );
                    platformButtons.add( tb );
                    platformGroup.getToggles().add( tb );
                    platformPanel.getChildren().add( tb );
                    if( pn.equals( mps.selected ))
                        tb.setSelected( true );
                }
                // TODO remove old
                add( platformPanel, 0, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }
    public void receiveMessage( MessageChannelSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                if(platformPanel != null)
                    getChildren().remove( platformPanel );
                platformPanel = new VBox();
                platformGroup = new ToggleGroup();
                platformButtons = new LinkedList<>();
                for (String pn : mps.names)
                {
                    PlatformButton tb = new PlatformButton( pn );
                    platformButtons.add( tb );
                    platformGroup.getToggles().add( tb );
                    platformPanel.getChildren().add( tb );
                    if( pn.equals( mps.selected ))
                        tb.setSelected( true );
                }
                // TODO remove old
                add( platformPanel, 0, 0 );
                break;
            default:
                logger.warning( "Unexpected message cmd" );
                break;
        }
    }
}
