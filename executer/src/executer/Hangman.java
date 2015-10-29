package executer;

import java.awt.Graphics;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Hangman extends JScrollPane implements Runnable
{
    private Element target;
    
    public void doIt(Element target)
    {
        this.target = target;
        new Thread( this ).start();
    }
    @Override
    public void run()
    {
        PrintStream ps = getPrintStream();
        ProcessBuilder pb = new ProcessBuilder();
        String cmd = parameter.getRootObject().getString("CommandPath");
        String para1 = target.getRtsp();
        pb.command( cmd , para1 );
        pb.redirectErrorStream(true);
        pb.inheritIO();
        ps.println("toString() " + pb );
        ps.println("Executing" + target.getName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>");
        try
        {
            pb.start();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    ///////////////////////////////////////////////////////////////
    private static final long serialVersionUID = 1L;
    private JTextArea ita = new MyTextArea();
    private UpdatePort updatePort = new UpdatePort();
    private final Parameter parameter;

    private final String tooltip;

    public Hangman(String tooltip, Parameter parameter)
    {
        this.tooltip = tooltip;
        this.parameter = parameter;

        setViewportView(ita);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void clr()
    {
        updatePort.clr();
    }

    public void setText(String s)
    {
        updatePort.setText(s);
    }

    class MyTextArea extends JTextArea
    {
        private static final long serialVersionUID = 1L;

        MyTextArea()
        {
            setEditable(false);
            setFocusable(false);
            setToolTipText(tooltip);
        }

        @Override
        public void paint(Graphics g)
        {
            if (updatePort.checkChange())
            {
                setText(updatePort.get().toString());

                JScrollBar vs = Hangman.this.getVerticalScrollBar();
                vs.setValue(vs.getMaximum());
            }
            super.paint(g);
        }
    }

    private class UpdatePort extends OutputStream
    {
        StringBuilder isb;

        boolean changeFlag = false;

        UpdatePort()
        {
            isb = new StringBuilder();
        }

        //- destination side usage
        public synchronized boolean checkChange()
        {
            return (changeFlag);
        }

        public synchronized StringBuilder get()
        {
            changeFlag = false;
            return (isb);
        }

        //------ source side usage
        public synchronized void clr()
        {
            isb.setLength(0);
            changeFlag = true;
            repaint();
        }

        public synchronized void setText(String s)
        {
            isb.setLength(0);
            isb.append(s);
            changeFlag = true;
            repaint();
        }

        //----------- implementing OutputStream
        @Override
        public synchronized void write(int b) throws IOException
        {
            isb.append((char) b);
            changeFlag = true;
            repaint();
        }

        @Override
        public synchronized void write(byte b[]) throws IOException
        {
            isb.append(new String(b));
            changeFlag = true;
            repaint();
        }

        @Override
        public synchronized void write(byte b[], int off, int len) throws IOException
        {
            if (b == null)
            {
                throw new NullPointerException();
            }
            else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0))
            {
                throw new IndexOutOfBoundsException();
            }
            else if (len == 0)
            {
                return;
            }
            isb.append(new String(b, off, len));
            changeFlag = true;
            repaint();
        }

        @Override
        public synchronized void flush() throws IOException
        {
        }

        @Override
        public synchronized void close() throws IOException
        {
        }
    }

    public PrintStream getPrintStream()
    {
        //return( new PrintStream( new UpdatePort() ) ); this is wrong !!!!!
        return (new PrintStream(updatePort));
    }

}
