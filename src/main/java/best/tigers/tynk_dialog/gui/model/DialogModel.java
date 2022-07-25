package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.gui.view.Observer;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;

public class DialogModel implements ListModel, Observer {
  private final ArrayList<DialogPageModel> pages;
  private final ArrayList<ListDataListener> listeners;

  public DialogModel() {
    pages = new ArrayList<>();
    listeners = new ArrayList<>();
  }

  public void addPage(DialogPageModel newPage) {
    newPage.attachSubscriber(this);
    pages.add(newPage);
    notifyListeners();
  }

  public void deletePage(DialogPageModel removedPage) {
    pages.remove(removedPage);
    notifyListeners();
  }

  @Override
  public int getSize() {
    return pages.size();
  }

  @Override
  public Object getElementAt(int index) {
    return pages.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.add(l);
  }

  public void notifyListeners() {
    var event = new ListDataEvent(pages, ListDataEvent.CONTENTS_CHANGED, 0, pages.size() - 1);
    for (var listener : listeners) {
      listener.contentsChanged(event);
    }
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.remove(l);
  }

  @Override
  public void update() {
    notifyListeners();
  }
}
