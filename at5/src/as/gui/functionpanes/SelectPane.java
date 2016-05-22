package as.gui.functionpanes;

import java.util.List;
import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.interim.message.MessageClamp;
import as.interim.message.MessagePlatformSelect;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SelectPane extends GridPane implements IC_FunctionPane, IL_Receiver, Runnable
{
    private final Logger logger = LoggingInit.get(this);

    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton()
        {
            setText("What do You want");
            setOnAction(this);
        }

        @Override
        public void handle(ActionEvent event)
        {
            System.out.println("No Meaning");
        }
    }

    class PlatformButton extends ToggleButton implements EventHandler<ActionEvent>
    {
        PlatformButton(String platformName)
        {
            setText(platformName);
            setOnAction(this);
        }

        @Override
        public void handle(ActionEvent event)
        {
            System.out.println("No Meaning");
        }
    }

    // private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;
    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private final MessageClamp messageClamp = new MessageClamp();
    private List<Button> platformButtons;
    private ToggleGroup platformGroup;
    private VBox platformPanel;
    
    public SelectPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add(new SelectionButton("Select", this, rootParent));

        setGridLinesVisible(true);
        setPadding(new Insets(25, 25, 25, 25));
        setVgap(2.0);
        setHgap(1.0);

        add(new MyButton(), 0, 3);
        StaticStarter.getClientPort().register(receivePlatformSelect, this);
    }

    @Override
    public void setActive(boolean active)
    {
        if (active)
        {
            rootParent.getHeaderInterface().setTitle("Select");
            transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.GET_LIST;
            StaticStarter.getClientPort().publish(transmittPlatformSelect);
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
    @Override
    public void receive(MessageBase message)
    {
        Platform.runLater(this);
        messageClamp.stall(message);
    }

    @Override
    public void run()
    {
        if (StaticConst.LOG_INTERIM) logger.info("Select Pane gots Message");
        /// must feed to awt Thread
        platformPanel = new VBox();
        platformGroup = new ToggleGroup();
        MessagePlatformSelect mps = (MessagePlatformSelect)(messageClamp.aquire());
        for(String pn : mps.names)
        {
            ToggleButton tb = new ToggleButton(pn);
            platformGroup.getToggles().add(tb);
            platformPanel.getChildren().add(tb);
        }
        add( platformPanel, 0,0);
        messageClamp.release();
    }
}
