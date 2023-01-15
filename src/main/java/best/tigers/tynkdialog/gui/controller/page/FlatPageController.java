package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.FlatPageEditorView;

public class FlatPageController extends AbstractPageController {
  private final FlatPageEditorView view;
  private final FlatPageModel model;

  public FlatPageController(FlatPageModel pageModel, FlatPageEditorView pageView) {
    this.model = pageModel;
    this.view = pageView;
    initView();
  }

  @Override
  FlatPageEditorView getView() {
    return view;
  }


  @Override
  public void saveChanges() {
    String newFlat = view.getFlat();
    if (!model.getFlat().equals(newFlat)) {
      model.setFlat(newFlat);
    }
  }

  @Override
  public void setupViewShortcuts() {
    super.setupViewShortcuts();
  }
}
