package wwt1;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.LoggingInit;

public class MYApplication5 extends Application
{
    Logger logger = LoggingInit.get( this );

    class MyScene extends Scene
    {
        MyScene(int w, int h)
        {
            super( new RootGrid(), w, h, Color.ALICEBLUE );
        }
    }
    
    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        int w,h;

        if( isBigger())
        {
            primaryStage.initStyle( StageStyle.UNDECORATED );
        }
        else
        {
            primaryStage.setFullScreen( true );
            primaryStage.initStyle( StageStyle.UNDECORATED );
        }
        primaryStage.setTitle("Grid Root Test");
        primaryStage.setScene( new MyScene(StaticConst.SCREEN_WIDTH, StaticConst.SCREEN_HEIGHT) );
        primaryStage.show();

    }
    private boolean isBigger()
    {
        boolean bigger;
        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        bigger = b.getWidth() > StaticConst.SCREEN_WIDTH;
        bigger |= b.getHeight() > StaticConst.SCREEN_HEIGHT;
        logger.fine( "Screen Size >>>>" + b.toString() );
        return bigger;
    }
}

