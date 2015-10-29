package starter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

public class at1
{

	public static void main(String[] args)
	{
		d1();
		Central.start();
	}

	private static void d1()
	{
		Mixer.Info[] fmi = AudioSystem.getMixerInfo();
		for (Mixer.Info mi : fmi)
		{
			Mixer mixer = AudioSystem.getMixer(mi);
			Line.Info[] isl = mixer.getSourceLineInfo();
			Line.Info[] itl = mixer.getTargetLineInfo();
			// if( itl.length > 0)
			{
				error.log("--------------------------------------------------------------");
				error.log("Src Count " + isl.length + " Target size " + itl.length);
				error.log(mi.toString());
				error.log("Name " + mi.getName());
				error.log("Version " + mi.getVersion());
				error.log("Vendor " + mi.getVendor());
				error.log("Description " + mi.getDescription());
				error.log("~~~");
				dumpLines(isl, "  >> SourceLine  : ");
				dumpLines(itl, "  >> TargetLine  : ");
			}
		}
	}

	private static void dumpLines(Line.Info[] fil, String prefix)
	{
		for (Line.Info li : fil)
		{
			error.log(prefix + li.getLineClass().getName());
		}
	}
}
