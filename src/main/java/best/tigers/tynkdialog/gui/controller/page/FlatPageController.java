package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.FlatPageEditorView;

import java.awt.event.WindowEvent;

public class FlatPageController implements PageController {
  private final FlatPageEditorView view;
  private final FlatPageModel model;

  FlatPageController(FlatPageModel model) {
    this.model = model;
    view = new FlatPageEditorView(model).init();
    view.attachSaveAction(this::saveAndExit);
  }

  @Override
  public FlatPageControllerFactory getFactory() {
    return new FlatPageControllerFactory();
  }

  @Override
  public FlatPageEditorView getView() {
    return view;
  }

  @Override
  public AbstractPageModel getModel() {
    return model;
  }

  @Override
  public void saveChanges() {
    String newFlat = view.getFlat();
    if (!model.getFlat().equals(newFlat)) {
      model.setFlat(newFlat);
    }
  }

  @Override
  public void saveAndExit() {
    saveChanges();
    view.getPanel().dispatchEvent(new WindowEvent(view.getFrame(), WindowEvent.WINDOW_CLOSING));
    view.getFrame().dispose();
  }

  @Override
  public void setupViewShortcuts() {
    view.attachSaveAction(this::saveAndExit);
    view.attachContinueAction(this::saveAndExit);
  }
}
