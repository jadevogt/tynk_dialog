package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.neo.NeoBranchPageEditorView;

public class BranchPageController extends AbstractPageController {
  private NeoBranchPageEditorView view;
  private BranchPageModel model;

  public BranchPageController(AbstractPageModel model, AbstractPageEditorView view) {
    this.model = (BranchPageModel) model;
    this.view = (NeoBranchPageEditorView) view;
    initView();
  }

  @Override
  public BranchPageModel getModel() {
    return model;
  }

  @Override
  NeoBranchPageEditorView getView() {
    return view;
  }
}
