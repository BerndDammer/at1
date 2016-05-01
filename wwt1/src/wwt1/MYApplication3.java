package wwt1;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logging.LoggingInit;

public class MYApplication3 extends Application
{
    Logger logger = LoggingInit.get( this );

    class RootGroup extends Group
    {
        RootGroup ()
        {
            WebView webView = new WebView();
            getChildren().add( webView );

            WebEngine webEngine = webView.getEngine();
            //webEngine.load( "http://docs.oracle.com/javase/8/javafx/api/index.html" );
            webEngine.load( "https://login.live.com" );
        }
    }

    class MyScene extends Scene
    {
        MyScene ()
        {
            super( new RootGroup(), 1600, 900, Color.ALICEBLUE );
            Screen root = Screen.getPrimary();
            Rectangle2D b = root.getBounds();
        }
    }

    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        // TODO Auto-generated method stub
        //primaryStage.initStyle( StageStyle.UNDECORATED );
        primaryStage.setTitle( "wwt3" );
        primaryStage.setScene( new MyScene() );
        primaryStage.show();

        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        logger.info( b.toString() );
    }
}
