package best.tigers.tynkdialog.gui.controller;

import best.tigers.tynkdialog.gui.controller.page.AbstractPageController;
import best.tigers.tynkdialog.gui.factories.AbstractPageMvcFactory;
import best.tigers.tynkdialog.gui.factories.FlatPageMvcFactory;
import best.tigers.tynkdialog.gui.factories.TalkPageMvcFactory;
import best.tigers.tynkdialog.gui.model.DialogModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.DialogEditorView;
import best.tigers.tynkdialog.gui.view.components.AutoResizingTable;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.util.Log;
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
import lombok.Getter;

public class DialogController {

  private final DialogEditorView view;
  @Getter
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

  static AbstractPageMvcFactory getMvcFactory(String pageKind) {
    AbstractPageMvcFactory factory;
    switch (pageKind) {
      case "flat" -> factory = new FlatPageMvcFactory();
      case "talk" -> factory = new TalkPageMvcFactory();
      default -> {
        Log.info("Couldn't find a MvcFactory for pageKind \"" + pageKind
            + ",\" falling back to talkKind.");
        factory = new TalkPageMvcFactory();
      }
    }
    return factory;
  }

  static AbstractPageMvcFactory getMvcFactory(AbstractPageModel pageModel) {
    return getMvcFactory(pageModel.getPage().getPageKind());
  }

  private void initDialogController() {
    // Setup view and shortcuts
    AutoResizingTable table = view.getTable();
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK, true);
    var ctrlNKey = "Ctrl+N released";
    view.attachFunctionalKeyboardShortcut(ctrlN, ctrlNKey, () -> addPage("talk"));

    KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    var enterMapKey = "Enter";
    table.attachFunctionalKeyboardShortcut(enterKey, enterMapKey, this::editPage);

    view.addEditorActions(new EditAction(), buildAddAction("talk"), buildAddAction("flat"),
        new DeleteAction(), new SwapUpAction(), new SwapDownAction());
    view.attachFocusListener(this::saveTitle);

    MouseAdapter doubleClickAdapter = buildDoubleClickAdapter();
    table.addMouseListener(doubleClickAdapter);
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
          addPage("talk");
          return;
        }
        editPage();
      }
    };
  }

  public JPanel getPanel() {
    return view.getPanel();
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

  void bindPageEditorShortcuts(AbstractPageModel model, AbstractPageEditorView view,
      AbstractPageController controller) {
    view.attachContinueAction(() -> {
      controller.saveAndExit();
      duplicateAndEditPage(model);
    });
    controller.setupViewShortcuts();
  }

  public void addPage(String kind) {
    var factory = getMvcFactory(kind);
    var newModel = factory.createNewPageModel();

    model.addPage(newModel);

    var newView = factory.createPageView(newModel);
    var newController = factory.createPageController(newModel, newView);
    bindPageEditorShortcuts(newModel, newView, newController);

    revalidateTable();
  }

  public void duplicateAndEditPage(AbstractPageModel oldModel) {
    var newModel = oldModel.clone();

    model.addPage(newModel);

    var oldIndex = model.getPageIndex(oldModel);
    var newIndex = model.getPageIndex(newModel);

    while (newIndex > oldIndex + 1) {
      model.swapListItems(newIndex--, newIndex);
    }

    var factory = getMvcFactory(oldModel);
    var newView = factory.createPageView(newModel);
    var newController = factory.createPageController(newModel, newView);
    bindPageEditorShortcuts(newModel, newView, newController);

    revalidateTable();
  }

  public void editPage() {
    var selectedModel = view.getSelectedModel();
    if (selectedModel == null) {
      java.awt.Toolkit.getDefaultToolkit().beep();
      return;
    }
    var factory = getMvcFactory(selectedModel);
    var newView = factory.createPageView(selectedModel);
    var newController = factory.createPageController(selectedModel, newView);
    bindPageEditorShortcuts(selectedModel, newView, newController);
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

  private AbstractAction buildAddAction(String pageKind) {
    var newAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addPage(pageKind);
      }
    };
    newAction.putValue(Action.NAME, "Add " + pageKind + " page");
    newAction.putValue(Action.SHORT_DESCRIPTION,
        "Create a new " + pageKind + " page and open it in the editor");
    return newAction;
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
