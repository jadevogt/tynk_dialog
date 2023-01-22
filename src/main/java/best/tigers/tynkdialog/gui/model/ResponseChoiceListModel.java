package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.TObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ResponseChoiceListModel extends AbstractModel
    implements ListModel<ChoiceResponseModel>, TObserver {

  private final ArrayList<ChoiceResponseModel> responses;
  private final ArrayList<ListDataListener> listDataListeners;

  public ResponseChoiceListModel() {
    this(new ArrayList<>());
  }

  public ResponseChoiceListModel(List<ChoiceResponseModel> responses) {
    this.responses = new ArrayList<>(responses);
    listDataListeners = new ArrayList<>();
  }

  public void addResponse(ChoiceResponseModel newResponse) {
    responses.add(newResponse);
    notifyListeners();
  }

  public void deleteResponse(ChoiceResponseModel removedResponse) {
    responses.remove(removedResponse);
    notifyListeners();
  }

  @Override
  public int getSize() {
    return responses.size();
  }

  @Override
  public ChoiceResponseModel getElementAt(int index) {
    if (index >= 0 && index <= responses.size() - 1) {
      return responses.get(index);
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
        new ListDataEvent(responses, ListDataEvent.CONTENTS_CHANGED, 0, responses.size() - 1);
    for (ListDataListener listener : listDataListeners) {
      listener.contentsChanged(event);
    }
    notifySubscribers();
  }

  public void swapListItems(int index1, int index2) {
    Collections.swap(responses, index1, index2);
    notifyListeners();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listDataListeners.remove(l);
  }

  @Override
  public void update() {
    notifyListeners();
  }

  public ArrayList<ChoiceResponseModel> getContent() {
    return new ArrayList<>(responses);
  }
}
