package executer;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MyPane extends JScrollPane
{
    private static final long serialVersionUID = 1L;
    private final MyList myList;
    private final Elements entries;
    private final Hangman hm;
    
    public MyPane( Elements entries, Hangman hm )
    {
        this.entries = entries;
        this.hm = hm;
        myList = new MyList();
        setViewportView(myList);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    public Element getSelected()
    {
        Element result = myList.getSelectedValue();
        return result;
    }
    public class MyList extends JList<Element>
    {
        private static final long serialVersionUID = 1L;

        private final DefaultListModel<Element> myModel = new DefaultListModel<Element>();

        MyList()
        {
            for(Element e : entries)
            {
                myModel.addElement(e);
            }
            setModel(myModel);
            setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            setSelectedIndex(0);
        }
    }
}