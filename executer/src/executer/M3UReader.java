package executer;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

public class M3UReader
{
    private Elements entries = new Elements();
    
    M3UReader(String filename)
    {
        try
        {
            readFile(filename);

        }
        catch (Exception e)
        {
            error.exception(e);
        }
    }
    Elements get()
    {
        return entries;
    }

    private void readFile(String fn) throws Exception
    {
        FileReader fr = new FileReader(new File(fn));
        LineNumberReader lr = new LineNumberReader(fr);

        while (true)
        {
            String line1 = lr.readLine();
            if(line1==null) break;
            if (line1.startsWith("#EXTINF:"))
            {
                String[] split = line1.split(",");
                String name = split[1];
                lr.readLine();
                String rtsp = lr.readLine();
                entries.add( new Element( name, rtsp));
            }
        }
        int breakpoint = entries.size();
        breakpoint++;
    }
}
