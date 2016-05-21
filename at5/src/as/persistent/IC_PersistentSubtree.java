package as.persistent;

import javax.json.JsonObject;

public interface IC_PersistentSubtree
{
    public JsonObject getObject();
    public void flush();
}
