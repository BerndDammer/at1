package template;

import com.sun.jna.Library;
import com.sun.jna.Native;

/** Simple example of native C POSIX library declaration and usage. */
public class ExampleOfPOSIX {
   public interface POSIX extends Library {
    public int chmod(String filename, int mode);
    public int chown(String filename, int user, int group);
    public int rename(String oldpath, String newpath);
    public int kill(int pid, int signal);
    public int link(String oldpath, String newpath);
    public int mkdir(String path, int mode);
    public int rmdir(String path);
   }

   public static void main(String[] args) {
    POSIX posix = (POSIX) Native.loadLibrary("c", POSIX.class);
    posix.mkdir("/tmp/newdir", 0777);
    posix.rename("/tmp/newdir","/tmp/renamedir");
   }
}