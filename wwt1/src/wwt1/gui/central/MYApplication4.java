package wwt1.gui.central;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.LoggingInit;

public class MYApplication4 extends Application
{
    Logger logger = LoggingInit.get( this );

    class DummyRootGroup extends Group
    {
        DummyRootGroup()
        {
        }
    }



    class MyScene extends Scene
    {
        private WebView webView = new WebView(); /// find a better way to init
        
        MyScene ()
        {
            super( new DummyRootGroup() );
            WebEngine webEngine = webView.getEngine();
            webEngine.load( "http://docs.oracle.com/javase/8/javafx/api/index.html" );
            setRoot( webView );
        }
    }

    //////////////////////////
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        // TODO Auto-generated method stub
        primaryStage.initStyle( StageStyle.UNDECORATED );
        //primaryStage.setTitle( "wwt4" );
        primaryStage.setFullScreen( true );
        primaryStage.setScene( new MyScene() );
        primaryStage.show();

        Screen root = Screen.getPrimary();
        Rectangle2D b = root.getBounds();
        logger.info( b.toString() );
    }
}
