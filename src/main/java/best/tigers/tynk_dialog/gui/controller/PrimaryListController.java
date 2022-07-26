package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.gui.controller.filters.JSONFilter;
import best.tigers.tynk_dialog.gui.controller.filters.TextFilter;
import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.PrimaryListModel;
import best.tigers.tynk_dialog.gui.view.PrimaryListView;
import best.tigers.tynk_dialog.gui.view.components.MenuBar;
import best.tigers.tynk_dialog.util.DialogFile;
import best.tigers.tynk_dialog.util.Log;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class PrimaryListController {
  private PrimaryListModel model;
  private PrimaryListView view;
  private DialogFile fileHandle;

  public PrimaryListController(ArrayList<Dialog> dialogFiles) {
    model = new PrimaryListModel(dialogFiles);
    view = new PrimaryListView(model);
    fileHandle = new DialogFile();
    addMenuItem(e -> addDialog(),
        "Add DialogFile",
        "Adds a DialogFile to the list, which may be populated with individual Pages",
        MenuBar.Menu.EDIT
    );
    addMenuItem(e -> removeCurrentDialog(),
        "Remove selected DialogFile",
        "Removes the DialogFile that is highlighted in the list on the left",
        MenuBar.Menu.EDIT
    );
    addMenuItem(e -> newFile(),
        "New file",
        "Creates a new JSON dialog file for editing",
        MenuBar.Menu.FILE);
    addMenuItem(e -> openFile(),
        "Open file",
        "Open a JSON dialog file from disk for editing",
        MenuBar.Menu.FILE);
    addMenuItem(e -> saveInPlace(),
        "Save",
        "Save the current file in place",
        MenuBar.Menu.FILE);
    addMenuItem(e -> saveAs(),
        "Save as...",
        "Select a new location and name for the current file",
        MenuBar.Menu.FILE);
    view.attachWindowEvent(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        exitOperation();
      }
    });
    }

    public PrimaryListController() {
    this(new ArrayList<>());
    }

  public void exitOperation() {
    var response = view.prompt();
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
    var response = view.prompt();
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
    var chooser = new JFileChooser();
    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
    var jFilter = new JSONFilter();
    var tFilter = new TextFilter();
    chooser.addChoosableFileFilter(jFilter);
    chooser.addChoosableFileFilter(tFilter);
    chooser.showDialog(null, "Select or Create");
    var newPath = chooser.getSelectedFile().getAbsolutePath();
    model.setPath(newPath);
    return newPath;
  }

  public void openFile() {
    // fileHandle = new DialogFile(path);
    var path = selectFile();
    fileHandle.setPath(path);
    var theDialogs = new ArrayList<Dialog>();
    try {
      theDialogs = fileHandle.readFile();
    } catch (Exception e) {
      System.out.println(e);
    }
    model = new PrimaryListModel(theDialogs, path);
    view.swapModel(model);
    model.setModified(false);
    view.update();
  }

  public void addMenuItem(ActionListener action, String shortText, String longText, MenuBar.Menu menu) {
    view.addMenuItem(action, shortText, longText, menu);
  }

  public void addDialog() {
    var newDialog = new DialogModel();
    var newPanel = new DialogController(newDialog);
    model.addDialog(newPanel);
  }

  public void removeCurrentDialog() {
    var selected = view.currentSelection();
    if (selected != null) {
      model.deleteDialog(selected);
    }
  }

  public void duplicateCurrentDialog() {
    var selected = view.currentSelection();
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
      } catch (IOException e) {
        System.out.println("IO ERROR");
      }
    }
    else {
      saveAs();
    }
  }

  public void saveAs() {
    var newPath = selectFile();
    Log.info("Setting path to " + newPath + "...");
    fileHandle.setPath(newPath);
    saveInPlace();
  }
}