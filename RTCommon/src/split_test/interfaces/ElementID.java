package split_test.interfaces;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import split_test.error;

// ---------------------------------------------**********************************************------------------------------
// ---------------------------------------------------------------------------------------------
// Helper
public class ElementID implements Comparable<ElementID>, Serializable
{
    private static final long serialVersionUID = 1L;

    private Inet4Address my;

    public ElementID(String id)
    {
        InetAddress mya;
        try
        {
            mya = InetAddress.getByName(id);
            if( mya instanceof Inet4Address)
            {
                my = (Inet4Address)mya;
            }
            else
            {
                throw new UnknownHostException();
            }
        }
        catch (UnknownHostException e)
        {
            my = null;
            error.exception(e);
        }
    }

    // @Override
    // TODO why not override
    // public boolean equals(ElementID id)
    // {
    // boolean result;
    // // TRAP: wrong compare
    // //return this.id.equals(id.id);
    //
    // result = this.id.equals(id.id);
    // return result;
    // }
    //
    // public boolean equals(String id)
    // {
    // return this.id.equals(id);
    // }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        if (o instanceof ElementID)
        {
            ElementID test = (ElementID) o;
            result = this.my.equals(test.my);
        }
        else if (o instanceof String)
        {
            result = this.my.getCanonicalHostName().equals(o);
        }
        else
        {
            result = this == o;
        }
        return result;
    }
    @Override
    public String toString()
    {
        //return my.getCanonicalHostName();
        return my.getHostAddress();
    }

    @Override
    public int compareTo(ElementID o)
    {
        //int result = 0;
        byte[] a = this.my.getAddress();
        byte[] b = o.my.getAddress();
        
        int i = 0;
        boolean comparing = true;
        while(comparing)
        {
            int test = a[i] - b[i];
            if( test != 0) return test;
            i++;
            if( i > 3 ) comparing = false;
        }
        
        return 0;
    }

}