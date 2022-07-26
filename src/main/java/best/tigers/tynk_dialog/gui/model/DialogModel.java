package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.game.DialogPage;
import best.tigers.tynk_dialog.gui.view.Observer;

import javax.json.JsonObject;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Collections;

public class DialogModel extends AbstractModel implements ListModel<DialogPageModel>, Observer {
  private final ArrayList<DialogPageModel> pages;
  private final ArrayList<ListDataListener> listDataListeners;
  private String title;

  public DialogModel() {
    this(new Dialog());
  }

  public DialogModel(Dialog dialog) {
    pages = new ArrayList<>();
    listDataListeners = new ArrayList<>();
    setTitle(dialog.getTitle());
    for (var page : dialog.getPages()) {
      addPage(new DialogPageModel(page));
    }
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
  public DialogPageModel getElementAt(int index) {
    return pages.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listDataListeners.add(l);
  }

  public void notifyListeners() {
    var event = new ListDataEvent(pages, ListDataEvent.CONTENTS_CHANGED, 0, pages.size() - 1);
    for (var listener : listDataListeners) {
      listener.contentsChanged(event);
    }
    notifySubscribers();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listDataListeners.remove(l);
  }

  @Override
  public void update() {
    notifyListeners();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
    notifySubscribers();
  }

  public void swapListItems(int index1, int index2) {
    Collections.swap(pages, index1, index2);
    notifyListeners();
  }

  public int getPageIndex(DialogPageModel pageModel) {
    return pages.indexOf(pageModel);
  }

  public int getPageCount() {
    return pages.size();
  }

  public Dialog getDialog() {
    var contents = new ArrayList<DialogPage>();
    for (var page : pages) {
      contents.add(page.getDialogPage());
    }
    return new Dialog(title, contents);
  }
}
