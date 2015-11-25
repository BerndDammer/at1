package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;

public class CubeMix extends EffectDialog implements IControlOutReceiver
{
    private static final long serialVersionUID = 1L;
    private static final boolean CBRT = true;

    public CubeMix()
    {
        startIt();
    }

    @Override
    public double nextSample(double l, double r)
    {
        double[] para = parent.getControllerPanelMapper().lockValuesForSample();
        double v = para[8];
        double nv = 1.0 - v;
        if(CBRT)
        {
            l = Math.cbrt(l);
            r = Math.cbrt(r);
        }
        double result = l * l * l * v * v * v + 3.0 * l * l * r * v * v * nv + 3.0 * l * r * r * v * nv * nv + r * r * r * nv * nv * nv;
        result *= 0.5;
        parent.getControllerPanelMapper().freeValuesForSample();
        return result;
    }

    @Override
    public void commitControls()
    {
        ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("mix", 0.0, +1.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 8);
    }
}
