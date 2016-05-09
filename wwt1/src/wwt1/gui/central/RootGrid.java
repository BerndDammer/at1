package wwt1.gui.central;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import logging.LoggingInit;
import wwt1.gui.functionpanes.HomePane;
import wwt1.gui.functionpanes.InstallPane;
import wwt1.gui.functionpanes.RunPane;
import wwt1.gui.functionpanes.SelectPane;
import wwt1.gui.headerbar.HeaderBar;
import wwt1.gui.interfaces.FunctionPane;
import wwt1.gui.interfaces.HeaderInterface;
import wwt1.gui.interfaces.RootParent;
import wwt1.gui.interfaces.SelectionInterface;
import wwt1.gui.selectionbar.ExitButton;
import wwt1.gui.selectionbar.SelectionBar;

public class RootGrid extends GridPane implements RootParent
{
    private final Logger logger = LoggingInit.get( this );

    private final HeaderBar headerBar = new HeaderBar( this );
    private final SelectionBar functionBar = new SelectionBar( this );
    private List<FunctionPane> functionPanes = new LinkedList<>();
    private FunctionPane activePane;

    public RootGrid()
    {
        setBackground( new Background( new BackgroundFill( Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY ) ) );
        add( headerBar, 0, 0, GridPane.REMAINING, 1 );

        activePane = new HomePane( this );
        functionPanes.add( activePane );
        functionPanes.add( new InstallPane(this) );
        functionPanes.add( new SelectPane(this) );
        functionPanes.add( new RunPane(this) );

        add( activePane.getPane(), 0, 1, GridPane.REMAINING, 1 );

        add( functionBar, 0, 2, GridPane.REMAINING, 1 );
        functionBar.add( new ExitButton() );
        
        defineStretching();
        activePane.setActive( true );
    }

    private final void defineStretching()
    {
        //////////// give resizing hints
        ObservableList<RowConstraints> rows = getRowConstraints();
        logger.info( "original row constraints size" + rows.size() );

        RowConstraints rc;

        rc = new RowConstraints();
        // rc.setPercentHeight( 0 );
        rc.setVgrow( Priority.NEVER );
        rc.setMinHeight( StaticConst.GU );
        rows.add( rc );
        rc = new RowConstraints();
        // rc.setPercentHeight( 100 );
        rc.setVgrow( Priority.ALWAYS );
        rows.add( rc );
        rc = new RowConstraints();
        // rc.setPercentHeight( 0 );
        rc.setVgrow( Priority.NEVER );
        rows.add( rc );

        ObservableList<ColumnConstraints> ccs = getColumnConstraints();
        logger.info( "original column constraints size" + ccs.size() );
        ColumnConstraints cc;

        cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        ccs.add( cc );
    }

    ///////////////////////////// implements RootParent
    @Override
    public SelectionInterface getSelectionInterface()
    {
        return functionBar;
    }

    @Override
    public HeaderInterface getHeaderInterface()
    {
        return headerBar;
    }

    @Override
    public void activateFunctionPane( FunctionPane functionPane )
    {
        activePane.setActive( false );
        getChildren().remove( activePane.getPane() );
        activePane = functionPane;
        add( activePane.getPane(), 0, 1, GridPane.REMAINING, 1 );
        requestLayout();
        activePane.setActive( true );
    }

}
