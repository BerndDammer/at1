import java.io.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import json.JsonControlFile;

class LauncherApps
{
    private static final ApplicationGroup[] ag = new ApplicationGroup[]
    {
        new ApplicationGroup
        (
            "Software Test",
            false,
            new ApplicationEntry[]
            {
                new ApplicationEntryInsideJVM(  "ResourceRead" )
                                                .setClp( new String[]{"-no_system_exit", "1", "2"} ),
                new ApplicationEntryOutsideJVM( "ResourceRead" )
                                                .usePlatformExtensions( true )
                                                .setExtDirs( "extensions" )
                                                .setClp( new String[]{"-no_system_exit", "1", "2","3 dabei"} ),
                new ApplicationEntryInsideJVM( "PingCircle 1", "PingCircle", "PingCircle.jar" )
                                               .setClp( new String[]{"-no_system_exit"} ),
                new ApplicationEntryInsideJVM( "PingCircle 4 IN ", "PingCircleAll4One", "PingCircle.jar" )
                                               .setClp( new String[]{"-no_system_exit", "autostart"} ),
                new ApplicationEntryOutsideJVM( "PingCircle 4 OUT", "PingCircleAll4One", "PingCircle.jar" )
                                               .setClp( new String[]{"-no_system_exit", "autostart"} ),
                new ApplicationEntryNativeScript( "ThreadTest" )
                                                .setComment("Console"),
            }
        ),
        new ApplicationGroup
        (
            "Environement",
            false,
            new ApplicationEntry[]
            {
                new ApplicationEntryDocument( ".." + File.separator + "pdf" + File.separator + "JavaDeployment.pdf" )
                                              .setComment("Wow this is made" ),
                new ApplicationEntryNativeScript( "ShowLX" )
                                              .setComment("\'bout Linux" ),
                new ApplicationEntryShowHomeDir( "." + PG.HOME_SUBDIR )
                                              .setComment("local data" ),
            }
        ),
        new ApplicationGroup
        (
            "Hardware Access",
            true,
            new ApplicationEntry[]
            {
                new ApplicationEntryInsideJVM( "GearsFSW" ).setClp( new String[]{"-no_system_exit"} ),
                new ApplicationEntryOutsideJVM( "GearsFSW" )
                                                .setExtDirs( "extensions" )
                                                .setClp( new String[]{"-no_system_exit"} )
                                                .setComment("OpenGL Test"),
                new ApplicationEntryOutsideJVM( "BlackBox", "BlackBox", "BlackBox.jar" + File.pathSeparator + "comm.jar" )
                                                .usePlatformExtensions( true )
                                                .setComment("serial"),
                new ApplicationEntryOutsideJVM( "ParallelBlackBox", "ParallelBlackBox", "ParallelBlackBox.jar" + File.pathSeparator + "comm.jar" )
                                                .usePlatformExtensions( true )
                                                .setComment("parallel"),
                new ApplicationEntryOutsideJVM( "JCommPatchSerial", "JCommPatchSerial",
                                                "JCommPatch.jar" + File.pathSeparator + "BlackBox.jar" + File.pathSeparator + "comm.jar" )
                                                .usePlatformExtensions( true )
                                                .setComment("Windows only"),
                new ApplicationEntryOutsideJVM( "JCommPatchParallel", "JCommPatchParallel",
                                                "JCommPatch.jar" + File.pathSeparator + "ParallelBlackBox.jar" + File.pathSeparator + "comm.jar" )
                                                .usePlatformExtensions( true )
                                                .setComment("Windows only"),
            }
        ),
    };
    private static final ApplicationGroup[] getAg()
    {
        int gi, ei;
        JsonControlFile.set( "launcher.json" );
        ApplicationGroup[] result = null;
        
        JsonObject ro = JsonControlFile.get();
        JsonArray groups = ro.getJsonArray("groups");
        result = new ApplicationGroup[ groups.size() ];
        for( gi = 0; gi < groups.size(); gi++ )
        {
            JsonObject group = groups.getJsonObject( gi );
            JsonArray js_entries = group.getJsonArray("entries");
            ApplicationEntry[] ae_entries = new ApplicationEntry[ js_entries.size() ]; 
            for( ei = 0; ei < js_entries.size(); ei++ )
            {
                ApplicationEntry ae;
                JsonObject entry = js_entries.getJsonObject(ei);
                String type = entry.getString("type");
                if( type.equals("embedded"))
                {
                    ae = new ApplicationEntryInsideJVM( entry.getString("cmd") ).setClp(new String[]{"-no_system_exit"} );
                }
                else if(type.equals("childvm"))
                {
                    //ae = new ApplicationEntryOutsideJVM( entry.getString("cmd") ).setClp(new String[]{"-no_system_exit"} );
                    ae = new ApplicationEntryOutsideJVM( entry.getString("cmd") );
                }
                else
                {
                    ae = null;
                }
                ae_entries[ei] = ae;
            }
            String headline = group.getString("headline" );
            result[gi]  = new ApplicationGroup( headline, false, ae_entries ); 
        }
        
        return( result );
    }

        public static void main(String[] args )
    {
        //new LauncherFrame( args, ag );
        new LauncherFrame( args, getAg() );
    }
}

