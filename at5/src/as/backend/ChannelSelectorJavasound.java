package as.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.MessageChannelSelect;
import as.persistent.IC_SubTreeBase;
import as.persistent.PersistentCentral;
import as.starter.LoggingInit;
import as.starter.StaticStarter;

public class ChannelSelectorJavasound extends ChannelSelectorBase
        implements IL_MessageBaseReceiver<MessageChannelSelect>
{
    private static final String TAG_INPUT = "Input";
    private static final String TAG_OUTPUT = "Output";

    private final Logger logger = LoggingInit.get( this );

    private final List<String> inputNames = new LinkedList<>();
    private final List<String> outputNames = new LinkedList<>();
    private String selectedInput = null;
    private String selectedOutput = null;
    private final MessageChannelSelect receiveChannelSelect = new MessageChannelSelect();
    private final MessageChannelSelect transmittChannelSelect = new MessageChannelSelect();
    private final IC_SubTreeBase para;
    private STATE state; 

    // JSON Value not in list ????
    public ChannelSelectorJavasound()
    {
        state = STATE.NO_PARA;
        selectedInput = null;
        selectedOutput = null;
        para = PersistentCentral.subChannelSelector();
        if (para.containsKey( TAG_INPUT ))
        {
            try
            {
                selectedInput = (String) para.get( TAG_INPUT );
            }
            catch ( Exception e)
            {
                logger.warning( "Error reading Input Channel Selection" );
            }
        }
        if (para.containsKey( TAG_OUTPUT ))
        {
            try
            {
                selectedOutput = (String) para.get( TAG_OUTPUT );
            }
            catch (Exception e)
            {
                logger.warning( "Error reading Output Channel Selection" );
            }
        }
            
    }

    @Override
    public void activeFromParent( boolean active )
    {
        inputNames.clear();
        outputNames.clear();
        if (active)
        {
            StaticStarter.getServerPort().register( receiveChannelSelect, this );

            Mixer.Info[] fmi = AudioSystem.getMixerInfo();
            for (Mixer.Info mi : fmi)
            {
                Line.Info[] inputLineInfos;
                Line.Info[] outputLineInfos;
                Mixer mixer = AudioSystem.getMixer( mi );
                String name = mi.getName();
                inputLineInfos = AudioSystem.getMixer( mi ).getTargetLineInfo();
                outputLineInfos = AudioSystem.getMixer( mi ).getSourceLineInfo();
                switch (getInputLineCount( mixer, inputLineInfos ))
                {
                    case 0:
                        logger.info( name + " has no input lines" );
                        break;
                    case 1:
                        inputNames.add( name );
                        break;
                    default:
                        logger.info( name + " has " + inputLineInfos.length + " input lines" );
                        break;

                }
                switch (getOutputLineCount( mixer, outputLineInfos ))
                {
                    case 0:
                        logger.info( name + " has no outpu lines" );
                        break;
                    case 1:
                        outputNames.add( name );
                        break;
                    default:
                        logger.info( name + " has " + outputLineInfos.length + " output lines" );
                        break;

                }
            }
        }
        else // active false
        {
            // fiewjfoew

        }

    }
    private boolean checkPara()
    {
        if( inputNames == null ) return false;
        if( outputNames == null ) return false;
        if( selectedInput == null ) return false;
        if( selectedOutput ==  null)return false;
        if( ! inputNames.contains( selectedInput )) return false;
        if( ! outputNames.contains( selectedOutput )) return false;
        return true;
    }
    private void buildAvaiableLists()
    {
        
    }

    private int getInputLineCount( Mixer mixer, Line.Info[] lis )
    {
        int result = 0;
        for (Line.Info li : lis)
        {
            try
            {
                Line line = mixer.getLine( li );
                if (line instanceof TargetDataLine)
                {
                    result++;
                }
                line.close();
            }
            catch (LineUnavailableException e)
            {
                logger.warning( "Ploblem getting line info" );
            }

        }
        return result;
    }

    private int getOutputLineCount( Mixer mixer, Line.Info[] lis )
    {
        int result = 0;
        for (Line.Info li : lis)
        {
            try
            {
                Line line = mixer.getLine( li );
                if (line instanceof SourceDataLine)
                {
                    result++;
                }
                line.close();
            }
            catch (LineUnavailableException e)
            {
                logger.warning( "Ploblem getting line info" );
            }
        }
        return result;
    }

    @Override
    public void receiveMessage( MessageChannelSelect message )
    {

    }
}
