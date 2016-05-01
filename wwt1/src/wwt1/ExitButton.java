package wwt1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitButton extends SelectionButton implements EventHandler<ActionEvent>
{
    public ExitButton()
    {
        super("Exit");
        setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event)
    {
        Platform.exit();
    }
}
