package as.gui.functionpanes;

import java.util.logging.Logger;

import as.gui.interfaces.IC_FunctionPane;
import as.gui.interfaces.IC_RootParent;
import as.gui.selectionbar.SelectionButton;
import as.starter.LoggingInit;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InfoPane extends GridPane implements IC_FunctionPane
{
    
    private final Logger logger = LoggingInit.get( this );

    private final IC_RootParent rootParent;

    public InfoPane(IC_RootParent rootParent)
    {
        this.rootParent = rootParent;
        rootParent.getSelectionInterface().add( new SelectionButton("Info", this, rootParent) );
        
        setPadding( new Insets( 10, 10, 10, 10 ) );
        setVgap( 3.0 );
        setHgap( 5.0 );

        add( new Label("OS Name : "), 0, 0 );
        add( new Label(System.getProperty("os.name")), 1, 0);
        add( new Label("OS Arch : "), 0, 1);
        add( new Label(System.getProperty("os.arch")), 1,1 );
        add( new Label("javafx.platform : "), 0, 2);
        add( new Label(System.getProperty("javafx.platform")), 1,2 );
        add( new Label("javafx.runtime.path : "), 0, 3);
        add( new Label(System.getProperty("javafx.runtime.path")), 1,3 );
        addProp("java.vm.name", 2, 0);
    }
    @Override
    public void setActive( boolean active )
    {
        logger.info( "Install switched" );
        if(active)
        {
            rootParent.getHeaderInterface().setTitle( "System Information" );
        }
    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }
    private void addProp(String name, int x, int y)
    {
        add( new Label(name + " : "), x, y);
        add( new Label(System.getProperty( name )), x+1,y );
    }
}
