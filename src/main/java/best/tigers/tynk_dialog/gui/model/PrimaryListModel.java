package best.tigers.tynk_dialog.gui.model;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.gui.controller.DialogController;
import best.tigers.tynk_dialog.gui.view.Observer;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PrimaryListModel extends AbstractModel implements ListModel<DialogController>, Observer {
  private final ArrayList<DialogController> dialogFiles;
  private final ArrayList<ListDataListener> listDataListeners;
  final private static String BLANK_PATH = "(New file)";
  private String title;
  private String path;
  private boolean modified;

  public PrimaryListModel() {
    this(new ArrayList<>());
  }

  public PrimaryListModel(ArrayList<Dialog> dialogs) {
    this (new ArrayList<>(), BLANK_PATH);
    modified = false;
  }

  public boolean isModified() {
    return modified;
  }

  public void setModified(boolean modified) {
    this.modified = modified;
  }

  public void setPath(String newPath) {
    this.path = newPath;
  }

  public String getPath() {
    return path;
  }

  public PrimaryListModel(ArrayList<Dialog> dialogs, String path) {
    this.path = path;
    dialogFiles = new ArrayList<>();
    listDataListeners = new ArrayList<>();
    for (Dialog dialog : dialogs) {
      DialogModel dialogModel = new DialogModel(dialog);
      addDialog(new DialogController(dialogModel));
    }
  }

  public void addDialog(DialogController newDialog) {
    newDialog.getModel().attachSubscriber(this);
    dialogFiles.add(newDialog);
    notifyListeners();
  }

  public void deleteDialog(DialogController removedDialog) {
    dialogFiles.remove(removedDialog);
    notifyListeners();
  }

  @Override
  public int getSize() {
    return dialogFiles.size();
  }

  @Override
  public DialogController getElementAt(int index) {
    return dialogFiles.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listDataListeners.add(l);
  }

  public void notifyListeners() {
    modified = true;
    ListDataEvent event = new ListDataEvent(dialogFiles, ListDataEvent.CONTENTS_CHANGED, 0, dialogFiles.size() - 1);
    for (ListDataListener listener : listDataListeners) {
      listener.contentsChanged(event);
    }
    notifySubscribers();
  }

  public void swapListItems(int index1, int index2) {
    Collections.swap(dialogFiles, index1, index2);
    notifyListeners();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listDataListeners.remove(l);
  }

  @Override
  public void update() {
    modified = true;
    notifyListeners();
  }

  public ArrayList<Dialog> getContent() {
    ArrayList<Dialog> contents = new ArrayList<Dialog>();
    for (DialogController currentDialog : dialogFiles) {
      contents.add(currentDialog.getModel().getDialog());
    }
    return contents;
  }
}