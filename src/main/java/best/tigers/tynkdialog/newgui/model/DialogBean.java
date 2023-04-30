package best.tigers.tynkdialog.newgui.model;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DialogBean implements ListModel<AbstractPageBean>, PropertyChangeListener {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final ArrayList<AbstractPageBean> pages;
    private final ArrayList<ListDataListener> listeners;
    private String title;

    public DialogBean() {
        pages = new ArrayList<>();
        listeners = new ArrayList<>();
        title = "";
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public DialogBean(String title) {
        this();
        this.title = title;
    }

    public DialogBean(String title, List<AbstractPageBean> beans) {
        this(title);
        addPages(beans);
    }

    public void addPages(List<AbstractPageBean> beans) {
        beans.forEach(this::addPage);
    }

    public void setTitle(String title) {
        var oldTitle = this.title;
        this.title = title;
        pcs.firePropertyChange("title", oldTitle, this.title);
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return pages.size();
    }

    @Override
    public AbstractPageBean getElementAt(int index) {
        return pages.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void addPage(AbstractPageBean bean) {
        bean.addPropertyChangeListener(this);
        pages.add(bean);
        var pageIndex = pages.indexOf(bean);
        var event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pageIndex, pageIndex);
        notifyListeners(event);
    }

    public void removePage(AbstractPageBean bean) {
        bean.removePropertyChangeListener(this);
        var pageIndex = pages.indexOf(bean);
        pages.remove(bean);
        var event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pageIndex, pageIndex);
        notifyListeners(event);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object changeSource = evt.getSource();
        if (changeSource instanceof AbstractPageBean pageBean) {
            var pageIndex = pages.indexOf(pageBean);
            var event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, pageIndex, pageIndex);
            notifyListeners(event);
        }
    }

    public void notifyListeners(ListDataEvent event) {
        switch (event.getType()) {
            case ListDataEvent.CONTENTS_CHANGED -> listeners.forEach(l -> l.contentsChanged(event));
            case ListDataEvent.INTERVAL_ADDED -> listeners.forEach(l -> l.intervalAdded(event));
            case ListDataEvent.INTERVAL_REMOVED -> listeners.forEach(l -> l.intervalRemoved(event));
        }
    }
}
