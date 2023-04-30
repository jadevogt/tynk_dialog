package best.tigers.tynkdialog.newgui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class FlatPageBean extends AbstractPageBean {
    private ArrayList<PropertyChangeListener> listeners;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private String flat;

    public String getFlat() {
        return flat;
    }

    public void setFlat(String newFlat) {
        var oldFlat = this.flat;
        flat = newFlat;
        pcs.firePropertyChange("flat", oldFlat, newFlat);
    }

    public FlatPageBean() {
        this.flat = "";
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        if (flat.equals("")) {
            return "Unnamed";
        }
        return flat;
    }
}
