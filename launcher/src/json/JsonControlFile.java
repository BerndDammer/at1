package json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class JsonControlFile
{
    static JsonControlFile meMyselfAndI = null; 
    private JsonObject rootObject = null;

    private JsonControlFile()
    {
        URL url = JsonControlFile.class.getResource( "launcher.json" );
        try
        {
            InputStream is = url.openStream();
            JsonReader rdr;
            rdr = Json.createReader(is);
            rootObject = rdr.readObject();
            rdr.close();
            is.close();
        }
        catch (IOException e)
        {
            rootObject = null;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private final JsonObject getRootObject()
    {
        return rootObject;
    }
    private final void setRootObject(String alterative)
    {
        // first try filesystem
        try
        {
            File file = new File(alterative);
            InputStream is = new FileInputStream(file);
            JsonReader rdr;
            rdr = Json.createReader(is);
            rootObject = rdr.readObject();
            rdr.close();
            is.close();
            return;
        }
        catch (IOException e)
        {
            // not file system try embedded
        }
        
        URL url = JsonControlFile.class.getResource(alterative);
        try
        {
            InputStream is = url.openStream();
            JsonReader rdr;
            rdr = Json.createReader(is);
            rootObject = rdr.readObject();
            rdr.close();
            is.close();
        }
        catch (IOException e)
        {
            System.out.println("reqestet json control file <" + alterative + "> not found ; use system default");
        }
        
    }

    //------------------------------------------ statics
    private static final void checkInstance()
    {
        if(meMyselfAndI == null)
            meMyselfAndI = new JsonControlFile();
    }

    public static final JsonObject get()
    {
        checkInstance();
        return meMyselfAndI.getRootObject();
    }
    public static void set(String alterative)
    {
        checkInstance();
        meMyselfAndI.setRootObject(alterative);
    }
}
