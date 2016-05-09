package wwt1.gui.selectionbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import wwt1.gui.central.StaticConst;
import wwt1.gui.interfaces.FunctionPane;
import wwt1.gui.interfaces.RootParent;

public class SelectionButton extends Button implements EventHandler<ActionEvent>
{
    private final FunctionPane functionPane;
    private final RootParent rootParent;

    public SelectionButton( String label, FunctionPane functionPane, RootParent rootParent )
    {
        this.functionPane = functionPane;
        this.rootParent = rootParent;
        setText( label );
        setPrefSize( StaticConst.GU, StaticConst.GU );
        setMinSize( StaticConst.GU, StaticConst.GU );
        // TODO Auto-generated constructor stub
        if (functionPane != null && rootParent != null)
            setOnAction( this );
    }

    @Override
    public void handle( ActionEvent event )
    {
        rootParent.activateFunctionPane( functionPane );
    }
}
