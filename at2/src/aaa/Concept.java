package aaa;

public class Concept
{

}

One panel for all platform specific parameter

iOperation may has more than 1 function
but goes to next level by one function



linux pulse_audio
jack

alsa 
libasound

PlatformBase -<Impl
  hat 
    PlatformPreparer -+ GUI
    PlatformPlayer - GUI
    
    IPlatform
    Operator uses Platform
    IOperation
    Platform injects to Operation
    
    IFunction
    Operator Uses Function
    
    ??? who has Panel
    ??? who has Dialog
    		
    		