package wwt1;

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

public class RootGrid extends GridPane
{
    private final Logger logger = LoggingInit.get( this );

    private final HeaderBar headerBar = new HeaderBar( this );
    private final SelectionBar functionBar = new SelectionBar( this );
    private List<FunctionPane> functionPanes = new LinkedList<>();

    public RootGrid()
    {
        FunctionPane activePane;

        setBackground( new Background( new BackgroundFill( Color.OLIVEDRAB, CornerRadii.EMPTY, Insets.EMPTY ) ) );

        add( headerBar, 0, 0, GridPane.REMAINING, 1 );
        // headerBar.
        activePane = new T1Pane();
        functionPanes.add( activePane );

        add( activePane.getPane(), 0, 1, GridPane.REMAINING, 1 );

        add( functionBar, 0, 2, GridPane.REMAINING, 1 );
        functionBar.add( new HomeButton() );
        functionBar.add( new SelectionButton( "Install" ) );
        functionBar.add( new SelectionButton( "Select" ) );
        functionBar.add( new SelectionButton( "Run" ) );
        functionBar.add( new ExitButton() );

        defineStretching();
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
        rc.setMinHeight( StaticConst.GAP );
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

}
