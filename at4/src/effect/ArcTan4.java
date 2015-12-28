package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public class ArcTan4 extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;
	public ArcTan4()
	{
		startIt();
	}


	@Override
	public double nextSample(double l, double r)
	{
	    double[] para = parent.getControllerPanelMapper().lockValuesForSample();
        double xr = para[4];
        double yr = para[5];
        double x,y,result;

        x = r-xr;
        y = l-yr;

        result = Math.atan2(y, x) / Math.PI;
		parent.getControllerPanelMapper().freeValuesForSample();
		return result;
	}

	@Override
	public void commitControls()
	{
	    ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("x", -1.0, +1.0 );
        parent.getControllerPanelMapper().add( controlReceiveParameter,4);
        controlReceiveParameter = new ControlReceiveParameter("y", -1.0, +1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,5);
	}
}

