package as.persistent;

import javax.json.JsonObject;

public class PersistentCentral
{
    private static JsonObject rootObject;
    
    private static class SubTree implements IC_PersistentSubtree
    {
        private JsonObject jo;
        private SubTree(String subname)
        {
            JsonObject so = rootObject.getJsonObject( subname );
            if( so != null)
            {
                this.jo = so;
            }
            else
            {
                
            }
            JsonObject so = rootObject.getJsonObject("Platform");
            IC_PersistentSubtree result = new SubTree(so);
            
        }
        @Override
        public JsonObject getObject()
        {
            return jo;
        }

        @Override
        public void flush()
        {
            update();
        }
    }
    static
    {
        // read file or create a new one
        rootObject = null;
    }
    private PersistentCentral()
    {
        // dont create instance
    }
    private static void update()
    {
        
    }
    public static IC_PersistentSubtree subPlatformSelector()
    {
        JsonObject so = rootObject.getJsonObject("Platform");
        IC_PersistentSubtree result = new SubTree(so);
        return result;
    }
}
