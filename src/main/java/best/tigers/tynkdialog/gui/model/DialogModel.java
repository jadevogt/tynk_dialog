package best.tigers.tynkdialog.gui.model;

import best.tigers.tynkdialog.exceptions.PageModelException;
import best.tigers.tynkdialog.game.Dialog;
import best.tigers.tynkdialog.game.page.FlatPage;
import best.tigers.tynkdialog.game.page.Page;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.TObserver;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class DialogModel extends AbstractModel implements ListModel<AbstractPageModel>, TObserver {

  private final ArrayList<AbstractPageModel> pages;
  private final ArrayList<ListDataListener> listDataListeners;
  private PageTableModel dptm;
  private String title;

  public DialogModel() {
    this(new Dialog());
  }

  public DialogModel(Dialog dialog) {
    pages = new ArrayList<>();
    dptm = new PageTableModel(pages);
    listDataListeners = new ArrayList<>();
    setTitle(dialog.getTitle());
    for (Page page : dialog.getPages()) {
      var pageKind = page.getPageKind();
      switch (pageKind) {
        case "talk" -> addPage(new TalkPageModel((TalkPage) page));
        case "flat" -> addPage(new FlatPageModel((FlatPage) page));
        default -> throw new PageModelException("No models exists for page kind " + pageKind + ".");
      }
    }
  }

  public void addPage(AbstractPageModel newPage) {
    newPage.attachSubscriber(this);
    pages.add(newPage);
    notifyListeners();
  }

  public void deletePage(AbstractPageModel removedPage) {
    pages.remove(removedPage);
    notifyListeners();
  }

  public ArrayList<AbstractPageModel> getPages() {
    return pages;
  }

  @Override
  public int getSize() {
    return pages.size();
  }

  @Override
  public AbstractPageModel getElementAt(int index) {
    return pages.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listDataListeners.add(l);
  }

  public void notifyListeners() {
    ListDataEvent event =
        new ListDataEvent(pages, ListDataEvent.CONTENTS_CHANGED, 0, pages.size() - 1);
    for (ListDataListener listener : listDataListeners) {
      listener.contentsChanged(event);
    }
    dptm = new PageTableModel(pages);
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

  public void setTitleSuppressed(String newTitle) {
    title = newTitle;
  }

  public void swapListItems(int index1, int index2) {
    if (index2 < pages.size() && index1 >= 0) {
      Collections.swap(pages, index1, index2);
    }
    notifyListeners();
  }

  public int getPageIndex(AbstractPageModel pageModel) {
    return pages.indexOf(pageModel);
  }

  public int getPageCount() {
    return pages.size();
  }

  public Dialog getDialog() {
    ArrayList<Page> contents = new ArrayList<>();
    for (AbstractPageModel page : pages) {
      contents.add(page.getPage());
    }
    return new Dialog(title, contents);
  }

  public PageTableModel getDptm() {
    return dptm;
  }
}
