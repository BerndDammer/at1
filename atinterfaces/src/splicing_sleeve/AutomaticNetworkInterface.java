package splicing_sleeve;


import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;



public class AutomaticNetworkInterface
{
    private static final List<NetworkInterface> result = new LinkedList<>();

    public static void dump()
    {
        scan();
        final PrintStream o = System.out;
        o.println("------------------Testing automatic NetworkInterfaces---------------------------");
        o.println("-----results-----------------------------------------");
        for (NetworkInterface ni : getList())
            o.println("... >>> is Good : " + ni.getName());

        o.println("------------------.----------------...._-=-------------------------");
    }

    public static List<NetworkInterface> getList()
    {
        return result;
    }

    public static void scan()
    {
        result.clear();

        Enumeration<NetworkInterface> eni;

        try
        {
            eni = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }

        while (eni.hasMoreElements())
        {
            try
            {
                boolean good = true;

                NetworkInterface ni = eni.nextElement();
                byte[] mac = ni.getHardwareAddress();
                good &= mac != null && mac.length == 6;
                int mtu = ni.getMTU();
                good &= mtu > 999 && mtu < 5982;
                good &= ni.getParent() == null;
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                if (inetAddresses != null)
                {
                    // must found at least one IPv4 address
                    boolean found = false;
                    while (inetAddresses.hasMoreElements())
                    {
                        InetAddress ia = inetAddresses.nextElement();
                        if (ia instanceof Inet4Address)
                            found = true;
                    }
                    good &= found;
                } else
                    good = false;

                List<InterfaceAddress> interfaceAddresses = ni.getInterfaceAddresses();
                if (interfaceAddresses != null)
                {
                    boolean found = false;
                    // found at least one IPV4 address
                    for (InterfaceAddress ina : interfaceAddresses)
                    {
                        InetAddress ia = ina.getAddress();
                        if (ia instanceof Inet4Address)
                            found = true;
                    }
                    good &= found;
                } else
                    good = false;


                good &= !ni.isLoopback();
                good &= !ni.isPointToPoint();
                good &= ni.isUp();
                good &= !ni.isVirtual();
                good &= ni.supportsMulticast();

                if (good)
                    result.add(ni);
            }
            catch (SocketException e)
            {
                // no good NetworkInterface
            }
        }
    }
}
