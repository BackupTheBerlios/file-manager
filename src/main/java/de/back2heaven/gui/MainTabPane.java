package de.back2heaven.gui;

import de.back2heaven.jbus.events.GUINotify;
import de.back2heaven.pattern.Observable;
import de.back2heaven.pattern.Observer;
import javax.swing.JTabbedPane;

public class MainTabPane extends JTabbedPane implements Observer {

    private static final long serialVersionUID = -5151810108646121060L;

    private boolean exsist(String name){
        for (int i=0; i < getTabCount(); i++){
            if (name.equals(getTitleAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    
    @Override
    public void update(Observable from, Object args) {
        // Swingutils invoke later
        if (args instanceof AddTab) {
            final AddTab t = (AddTab) args;
            if (exsist(t.getName())){
                throw new RuntimeException("Tab is not unique");
            }
            addTab(t.getName(), t.getTab());
            repaint();
        }
        if (args instanceof RemoveTab) {
            RemoveTab t = (RemoveTab)args;
            removeTabAt(indexOfTab(t.getName()));
        }
    }

    @Override
    public boolean accept(Object obj) {
        return obj instanceof GUINotify;
    }
}
