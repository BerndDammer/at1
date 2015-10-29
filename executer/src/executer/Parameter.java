package executer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;

public class Parameter
{
    private final String sdname;
    private final String hdname;
    private final M3UReader sdReader;
    private final M3UReader hdReader;
    private final JsonObject rootObject;
    
    Parameter()
    {
        JsonStructure rootFile = tryRootNode("parameter.json", false);
        JsonObject rootObject = (JsonObject)rootFile;
        this.rootObject = rootObject;
        
        sdname = rootObject.getString("sdfile");
        hdname = rootObject.getString("hdfile");
        sdReader = new M3UReader( sdname );
        hdReader = new M3UReader( hdname );
        
    }
    Elements getSD()
    {
        return sdReader.get();
    }
    Elements getHD()
    {
        return hdReader.get();
    }
    protected final JsonStructure tryRootNode(String controlFile, boolean expectArray)
    {
        JsonStructure js = null;
        //--------------
        if (true) // TODO always
        {
            InputStream is = null;
            JsonReader rdr = null;
            try
            {
                File file = new File(controlFile);
                is = new FileInputStream(file);
                rdr = Json.createReader(is);
                js = rdr.read();
            }
            catch (IOException e)
            {
                js = null;
            }
            finally
            {
                if (rdr != null)
                    rdr.close();
                if (is != null)
                    try
                    {
                        is.close();
                    }
                    catch (IOException e)
                    {
                        error.exception(e);
                    }
            }
        }
        if ( js == null)
        {
            URL url = Parameter.class.getResource(controlFile);
            InputStream is = null;
            JsonReader rdr = null;
            try
            {
                is = url.openStream();
                rdr = Json.createReader(is);
                js = rdr.read();
                rdr.close();
                is.close();
            }
            catch (IOException e)
            {
                error.exception(e);
            }
            finally
            {
                if (rdr != null)
                    rdr.close();
                if (is != null)
                    try
                    {
                        is.close();
                    }
                    catch (IOException e)
                    {
                        error.exception(e);
                    }
                
            }
        }
        return js;
    }
    public JsonObject getRootObject()
    {
        return rootObject;
    }
    
    
}
