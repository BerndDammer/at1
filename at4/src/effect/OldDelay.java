package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public class OldDelay extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;

	private static final double SAMPLE_FREQ = 48000.0;
    private static final int HISTORY_SIZE = (int)SAMPLE_FREQ;
	private int history_index = 0;
    private static final double hist_fact = SAMPLE_FREQ / 1000.0;
    
    private final double[] history = new double [HISTORY_SIZE]; 
	
	public OldDelay()
	{
	    for(int i = 0; i < HISTORY_SIZE;i++)
	        history[i] = 0.0;
		startIt();
	}


	@Override
	public double nextSample(double l, double r)
	{
	    double[] para = parent.getControllerPanelMapper().lockValuesForSample();

	    double s = (r* para[4] + l * (1.0 - para[4])) * 0.5;
	    
	    //store history
	    history_index ++;
	    if( history_index >= HISTORY_SIZE) history_index = 0;
	    //history[ history_index] = s;
	    
	    //get delay output
	    int delay_depth = (int)(para[5] * hist_fact);
	    int delay_index = history_index - delay_depth;
	    if(delay_index < 0) delay_index += HISTORY_SIZE;
	    double d = history[delay_index];
	    
	    //mix delay and original
	    double result = (d* para[6] + s * (1.0 - para[6]));
		
        history[ history_index] = result;

	    parent.getControllerPanelMapper().freeValuesForSample();
		return result;
	}

	@Override
	public void commitControls()
	{
	    ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("PU_Mix", 0.0, +1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,4);
        controlReceiveParameter = new ControlReceiveParameter("Time[ms]", 1.0, 100.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,5);
        controlReceiveParameter = new ControlReceiveParameter("mix", 0.0, +0.66666);
        parent.getControllerPanelMapper().add( controlReceiveParameter,6);
	}
}

