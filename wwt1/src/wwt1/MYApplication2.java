package wwt1;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logging.LoggingInit;

public class MYApplication2 extends Application
{
    Logger logger = LoggingInit.get( this );

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
    class RootGroup extends Group
    {
        RootGroup()
        {
            getChildren().add( new MyStackPane() );
        }
    }

    class MyScene extends Scene
    {
        MyScene()
        {
            super( new RootGroup(), 400, 500, Color.ALICEBLUE );
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

        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        logger.fine( b.toString() );
    }
    public static void launch1(String[] args)
    {
        launch(args);
    }

}

