package wwt1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MYApplication extends Application
{
    class MyButton extends Button implements EventHandler<ActionEvent>
    {
        MyButton()
        {
            setText("Say Hello World");
            setOnAction(this);
        }

        @Override
        public void handle( ActionEvent event )
        {
            System.out.println("Hello World!");
        }
    }
    class MyStackPane extends StackPane
    {
        MyStackPane()
        {
            getChildren().add( new MyButton() );
        }
    }
    class MyScene extends Scene
    {
        MyScene()
        {
            super( new MyStackPane(), 300, 250 );
        }
    }
    
    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        // TODO Auto-generated method stub
        primaryStage.setTitle("wwt1");
        primaryStage.setScene( new MyScene() );
        primaryStage.show();
    }
    public static void launch1(String[] args)
    {
        launch(args);
    }
}


