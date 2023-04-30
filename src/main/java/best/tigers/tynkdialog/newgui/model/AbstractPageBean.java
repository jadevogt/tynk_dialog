package best.tigers.tynkdialog.newgui.model;

import java.beans.PropertyChangeListener;

public abstract class AbstractPageBean {
    public abstract void addPropertyChangeListener(PropertyChangeListener listener);
    public abstract void removePropertyChangeListener(PropertyChangeListener listener);
}
