package controller.interfaces;


public interface IControlOutTransmitter
{
    public void setUseParameter(ControlReceiveParameter controlReceiveParameter);


    
    public static class ControlReceiveParameter
    {
        public final String name;
        public final double min;
        public final double max;
        
        public ControlReceiveParameter(String name,double min,double max )
        {
            this.name = name;
            this.min = min;
            this.max = max;
        }
    }

}