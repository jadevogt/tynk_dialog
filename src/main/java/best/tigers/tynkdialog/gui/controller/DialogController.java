package best.tigers.tynkdialog.gui.controller;

import best.tigers.tynkdialog.gui.model.DialogModel;
import best.tigers.tynkdialog.gui.model.DialogPageModel;
import best.tigers.tynkdialog.gui.view.DialogEditorView;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class DialogController {

  private final DialogEditorView view;
  private final DialogModel model;

  private DialogController(DialogModel model) {
    this.model = model;
    view = DialogEditorView.fromModel(model);
  }

  public static DialogController fromModel(DialogModel model) {
    var controller = new DialogController(model);
    controller.initDialogController();
    return controller;
  }

  public static DialogController newModel() {
    return DialogController.fromModel(new DialogModel());
  }

  private void initDialogController() {
    // Setup view and shortcuts
    var ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK, true);
    var ctrlNKey = "Ctrl+N released";
    view.attachFunctionalKeyboardShortcut(ctrlN, ctrlNKey, this::addPage);
    view.addEditorActions(new EditAction(), new AddAction(), new DeleteAction(), new SwapUpAction(),
        new SwapDownAction());
    view.attachFocusListener(this::saveTitle);

    var table = view.getTable();
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    var doubleClickAdapter = buildDoubleClickAdapter();
    table.addMouseListener(doubleClickAdapter);
    var enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    var enterMapKey = "Enter";
    table.attachFunctionalKeyboardShortcut(enterKey, enterMapKey, this::editPage);
  }


  MouseAdapter buildDoubleClickAdapter() {
    return new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // escape if this isn't a double click
        if (e.getClickCount() != 2) {
          return;
        }
        var table = view.getTable();
        int index = table.rowAtPoint(e.getPoint());
        // add a new page if the user clicked outside the rows
        if (index < 0) {
          addPage();
          return;
        }
        editPage();
      }
    };
  }

  public JPanel getPanel() {
    return view.getPanel();
  }

  public DialogModel getModel() {
    return model;
  }

  public void swapUp() {
    var page = view.getSelectedPage();
    int pageIndex = model.getPageIndex(page);
    // escape if we're at the top or if no page is selected
    if (pageIndex <= 0) {
      return;
    }
    model.swapListItems(pageIndex, pageIndex - 1);
    view.selectPage(pageIndex - 1);
  }

  public void swapDown() {
    var page = view.getSelectedPage();
    int pageIndex = model.getPageIndex(page);
    // escape if we're at the bottom or if no page is selected
    if (pageIndex >= model.getPageCount() - 1 || pageIndex < 0) {
      return;
    }
    model.swapListItems(pageIndex, pageIndex + 1);
    view.selectPage(pageIndex + 1);
  }

  public void saveTitle() {
    var newTitle = view.getTitle();
    model.setTitle(newTitle);
  }

  public void addPage() {
    var newModel = new DialogPageModel();
    model.addPage(newModel);
    var newController = DialogPageController.fromModel(newModel);
    var newView = newController.getView();
    newView.attachContinueAction(() -> {
      newController.saveAndExit();
      this.duplicateAndEditPage(newModel);
    });
    revalidateTable();
  }

  public void duplicateAndEditPage(DialogPageModel oldModel) {
    var newModel = new DialogPageModel();
    newModel.setSpeaker(oldModel.getSpeaker());
    newModel.setBlip(oldModel.getBlip());
    newModel.setBlipEnabled(oldModel.getBlipEnabled());
    newModel.setStyleEnabled(oldModel.getStyleEnabled());
    newModel.setTextBoxStyle(oldModel.getTextBoxStyle());
    model.addPage(newModel);
    var oldIndex = model.getPageIndex(oldModel);
    var newIndex = model.getPageIndex(newModel);
    while (newIndex > oldIndex + 1) {
      System.out.println(newIndex);
      System.out.println(oldIndex);
      model.swapListItems(newIndex--, newIndex);
    }
    var newController = DialogPageController.fromModelProceeding(newModel);
    var newView = newController.getView();
    newView.attachContinueAction(() -> {
      newController.saveAndExit();
      this.duplicateAndEditPage(newModel);
    });
    newView.getContentField().requestFocus();
    revalidateTable();
  }

  public void editPage() {
    var selectedModel = view.getSelectedModel();
    if (selectedModel != null) {
      var newController = DialogPageController.fromModel(selectedModel);
      var newView = newController.getView();
      newView.attachContinueAction(() -> {
        newController.saveAndExit();
        this.duplicateAndEditPage(selectedModel);
      });
    } else {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
  }

  public void deletePage() {
    if (view.getSelectedModel() != null) {
      model.deletePage(view.getSelectedModel());
    } else {
      java.awt.Toolkit.getDefaultToolkit().beep();
    }
    revalidateTable();
  }

  private void revalidateTable() {
    view.getTable().revalidate();
  }

  @Override
  public String toString() {
    return this.model.getTitle();
  }

  class AddAction extends AbstractAction {

    public AddAction() {
      putValue(Action.NAME, "Add");
      putValue(Action.SHORT_DESCRIPTION, "Create a new page and open it in the editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      addPage();
    }
  }

  class EditAction extends AbstractAction {

    public EditAction() {
      putValue(Action.NAME, "Edit");
      putValue(Action.SHORT_DESCRIPTION, "Open this page in the editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      editPage();
    }
  }

  class DeleteAction extends AbstractAction {

    public DeleteAction() {
      putValue(Action.NAME, "Remove");
      putValue(Action.SHORT_DESCRIPTION, "Delete the selected page");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      deletePage();
    }
  }

  class SwapUpAction extends AbstractAction {

    public SwapUpAction() {
      putValue(Action.NAME, "Up");
      putValue(Action.SHORT_DESCRIPTION, "Move the selected page up one spot");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      swapUp();
    }
  }

  class SwapDownAction extends AbstractAction {

    public SwapDownAction() {
      putValue(Action.NAME, "Down");
      putValue(Action.SHORT_DESCRIPTION, "Move the selected page down one spot");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      swapDown();
    }
  }
}
