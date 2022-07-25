package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.gui.model.DialogModel;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.view.DialogPageListView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DialogListViewController {
  private final DialogPageListView view;
  private final DialogModel model;

  public DialogListViewController(Dialog dialog) {
    model = new DialogModel();
    for (var page : dialog.getPages()) {
      model.addPage(new DialogPageModel(page));
    }
    view = new DialogPageListView(model);
    view.addEditorAction(new EditAction(view), "Edit page...");
    view.addEditorAction(new AddAction(model), "Add page...");
    view.addEditorAction(new DeleteAction(view, model), "Delete page");
    view.init();
  }
}

class AddAction extends AbstractAction {
  private final DialogModel model;

  public AddAction(DialogModel model) {
    this.model = model;
    putValue(Action.NAME, "+");
    putValue(Action.SHORT_DESCRIPTION, "Create a new page and open it in the editor");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    var newModel = new DialogPageModel();
    model.addPage(newModel);
    new DialogPageEditorController(newModel, true);
  }
}

class EditAction extends AbstractAction {
  private final DialogPageListView view;

  public EditAction(DialogPageListView view) {
    this.view = view;
    putValue(Action.NAME, "✎");
    putValue(Action.SHORT_DESCRIPTION, "Open this page in the editor");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    var selectedModel = view.getSelectedModel();
    if (selectedModel != null) {
      new DialogPageEditorController(view.getSelectedModel());
    }
  }
}

class DeleteAction extends AbstractAction {
  private final DialogModel model;
  private final DialogPageListView view;

  public DeleteAction(DialogPageListView view, DialogModel model) {
    this.model = model;
    this.view = view;
    putValue(Action.NAME, "-");
    putValue(Action.SHORT_DESCRIPTION, "Delete the selected page");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (view.getSelectedModel() != null) {
      model.deletePage(view.getSelectedModel());
    }
  }
}