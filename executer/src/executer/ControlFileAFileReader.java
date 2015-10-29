package starter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;

import error.error;

abstract class ControlFileAFileReader
{
    protected final JsonArray trySecondaryArray(String tagname )
    {
        JsonArray result = null;
        String filename = getRootObject().getString( tagname );
        result = (JsonArray)tryRootNode(filename, true);
        
        return result;
    }
    protected final JsonObject trySecondaryObject(String tagname )
    {
        JsonObject result = null;
        String filename = getRootObject().getString( tagname );
        result = (JsonObject)tryRootNode(filename, false);
        
        return result;
    }
    protected abstract JsonObject getRootObject();
    //protected abstract ControlFileEPublic getSingleton();
    
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
            URL url = ControlFileEPublic.class.getResource(controlFile);
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
}
