package wwt1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RootGrid extends GridPane
{
    private final HeaderBar headerBar = new HeaderBar(this);
    private final SelectionBar functionBar = new SelectionBar(this);
    //private List<Pane>  
    class HeadlineTitle extends Text
    {
        public HeadlineTitle()
        {
            super("Welcome");
            setAlignment(Pos.TOP_LEFT);
            setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        }
    }

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

    public RootGrid()
    {
        setGridLinesVisible(true);
        setPadding(new Insets(25, 25, 25, 25));
        setVgap(2.0);
        setHgap(1.0);
        // add( new MyButton(),0, GridPane.REMAINING );

        add(new HeadlineTitle(), 0, 0, GridPane.REMAINING, 1);
        Label pw = new Label("Password:");
        add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        add(pwBox, 0, 2);
        add(new MyButton(), 0, 3);
        add( functionBar, 0,4,GridPane.REMAINING, 1);
        functionBar.add( new HomeButton() );
        functionBar.add( new SelectionButton("Install") );
        functionBar.add( new SelectionButton("Select") );
        functionBar.add( new SelectionButton("Run") );
        functionBar.add( new ExitButton() );
    }
}
