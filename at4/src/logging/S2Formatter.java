/**
 * 
 */
package logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author manni1user
 *
 */
public class S2Formatter extends Formatter
{
    /**
     * 
     */
    public S2Formatter()
    {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(LogRecord record)
    {
        String result;
        
        result = String.format("%7s %d %s\n", record.getLevel(), record.getMillis(), record.getMessage() );
        // TODO Auto-generated method stub
        return result;
    }
}
