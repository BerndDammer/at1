package wwt1;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logging.LoggingInit;

public class MYApplication5 extends Application
{
    Logger logger = LoggingInit.get( this );

    class MyScene extends Scene
    {
        MyScene()
        {
            super( new RootGrid(), 400, 500, Color.ALICEBLUE );
        }
    }
    
    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Grid Root Test");
        primaryStage.setScene( new MyScene() );
        primaryStage.show();

        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        logger.fine( "Screen Size >>>>" + b.toString() );
    }
}

