package best.tigers.tynkdialog.gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class GenericListModel<T> extends AbstractModel
    implements ListModel<T> {

  private final ArrayList<T> items;
  private final ArrayList<ListDataListener> listDataListeners;

  public GenericListModel() {
    this(new ArrayList<>());
  }

  public GenericListModel(List<T> items) {
    this.items = new ArrayList<>(items);
    listDataListeners = new ArrayList<>();
  }

  public void addItem(T newItem) {
    items.add(newItem);
    notifyListeners();
  }

  public void deleteResponse(T removedItem) {
    items.remove(removedItem);
    notifyListeners();
  }

  @Override
  public int getSize() {
    return items.size();
  }

  @Override
  public T getElementAt(int index) {
    if (index >= 0 && index <= items.size() - 1) {
      return items.get(index);
    } else {
      return null;
    }
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listDataListeners.add(l);
  }

  public void notifyListeners() {
    ListDataEvent event =
        new ListDataEvent(items, ListDataEvent.CONTENTS_CHANGED, 0, items.size() - 1);
    for (ListDataListener listener : listDataListeners) {
      listener.contentsChanged(event);
    }
  }

  public void swapListItems(int index1, int index2) {
    Collections.swap(items, index1, index2);
    notifyListeners();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listDataListeners.remove(l);
  }

  public ArrayList<T> getContent() {
    return new ArrayList<>(items);
  }
}
