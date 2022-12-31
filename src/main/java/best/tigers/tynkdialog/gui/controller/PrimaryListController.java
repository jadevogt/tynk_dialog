package best.tigers.tynkdialog.gui.controller;

import best.tigers.tynkdialog.exceptions.DialogFileIOException;
import best.tigers.tynkdialog.game.Dialog;
import best.tigers.tynkdialog.gui.controller.filters.JSONFilter;
import best.tigers.tynkdialog.gui.controller.filters.TextFilter;
import best.tigers.tynkdialog.gui.model.PrimaryListModel;
import best.tigers.tynkdialog.gui.view.PrimaryListView;
import best.tigers.tynkdialog.gui.view.components.MenuBar;
import best.tigers.tynkdialog.util.DialogFile;
import best.tigers.tynkdialog.util.Log;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;

public class PrimaryListController {

  private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
  private final PrimaryListView view;
  private final DialogFile fileHandle;
  private PrimaryListModel model;

  public PrimaryListController(ArrayList<Dialog> dialogFiles) {
    model = new PrimaryListModel(dialogFiles);
    view = PrimaryListView.fromModel(model);
    fileHandle = new DialogFile();
    addMenuItem(
        e -> addDialog(),
        "Add DialogFile",
        "Adds a DialogFile to the list, which may be populated with individual Pages",
        MenuBar.Menu.EDIT);
    addMenuItem(
        e -> removeCurrentDialog(),
        "Remove selected DialogFile",
        "Removes the DialogFile that is highlighted in the list on the left",
        MenuBar.Menu.EDIT);
    addMenuItem(
        e -> newFile(),
        "New file",
        "Creates a new JSON dialog file for editing",
        MenuBar.Menu.FILE);
    addMenuItem(
        e -> openFile(),
        "Open file",
        "Open a JSON dialog file from disk for editing",
        MenuBar.Menu.FILE);
    addMenuItem(e -> saveInPlace(), "Save", "Save the current file in place", MenuBar.Menu.FILE);
    addMenuItem(
        e -> saveAs(),
        "Save as...",
        "Select a new location and name for the current file",
        MenuBar.Menu.FILE);
    view.attachWindowEvent(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            exitOperation();
          }
        });
  }

  public PrimaryListController() {
    this(new ArrayList<>());
  }

  public static void launch() {
    var primaryController = new PrimaryListController();
  }

  public void exitOperation() {
    int response = view.prompt();
    switch (response) {
      case 0:
        saveInPlace();
      case 1:
        break;
      case 2:
        return;
    }
    System.exit(0);
  }

  public void newFile() {
    int response = view.prompt();
    switch (response) {
      case 0:
        saveInPlace();
      case 1:
        break;
      case 2:
        return;
    }
    model = new PrimaryListModel();
    view.swapModel(model);
    model.setModified(false);
    view.update();
  }

  public String selectFile() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
    JSONFilter jFilter = new JSONFilter();
    TextFilter tFilter = new TextFilter();
    chooser.addChoosableFileFilter(jFilter);
    chooser.addChoosableFileFilter(tFilter);
    chooser.showDialog(null, "Select or Create");
    String newPath = chooser.getSelectedFile().getAbsolutePath();
    model.setPath(newPath);
    return newPath;
  }

  public void openFile() {
    // fileHandle = new DialogFile(path);
    String path = selectFile();
    fileHandle.setPath(path);
    ArrayList<Dialog> theDialogs = new ArrayList<>();
    try {
      theDialogs = fileHandle.readFile();
    } catch (DialogFileIOException e) {
      e.printStackTrace();
    }
    model = new PrimaryListModel(theDialogs, path);
    view.swapModel(model);
    model.setModified(false);
    view.update();
  }

  public void addMenuItem(
      ActionListener action, String shortText, String longText, MenuBar.Menu menu) {
    view.addMenuItem(action, shortText, longText, menu);
  }

  public void addDialog() {
    var newPanel = DialogController.newModel();
    if (!view.getCurrentRoom().isEmpty()) {
      newPanel.getModel().setTitle(view.getCurrentRoom());
    }
    model.addDialog(newPanel);
  }

  public void removeCurrentDialog() {
    DialogController selected = view.currentSelection();
    if (selected != null) {
      view.getDialogList().setSelectedValue(selected, false);
      model.deleteDialog(selected);
    }
  }

  public void duplicateCurrentDialog() {
    DialogController selected = view.currentSelection();
    if (selected != null) {
      // This isn't going to work until I implement deep copies
      model.addDialog(selected);
    }
  }

  public void saveInPlace() {
    if (fileHandle.isCustomized()) {
      try {
        fileHandle.writeFile(model.getContent());
        model.setModified(false);
        view.update();
      } catch (DialogFileIOException e) {
        e.printStackTrace();
      }
    } else {
      saveAs();
    }
  }

  public void saveAs() {
    String newPath = selectFile();
    Log.info("Setting path to " + newPath + "...");
    fileHandle.setPath(newPath);
    saveInPlace();
  }
}
