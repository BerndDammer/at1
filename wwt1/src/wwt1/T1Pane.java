package wwt1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class T1Pane extends GridPane implements FunctionPane
{
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton()
        {
            setText("Say Hello World");
            setOnAction(this);
        }

        @Override
        public void handle(ActionEvent event)
        {
            System.out.println("Hello World!");
        }
    }

    T1Pane()
    {
        setGridLinesVisible(true);
        setPadding(new Insets(25, 25, 25, 25));
        setVgap(2.0);
        setHgap(1.0);

        Label pw = new Label("Password:");
        add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        add(pwBox, 0, 2);
        add(new MyButton(), 0, 3);

    }

    /////////////////////// vector implements
    @Override
    public Pane getPane()
    {
        // TODO Auto-generated method stub
        return this;
    }
}
