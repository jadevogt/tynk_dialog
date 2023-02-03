package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.neo.NeoFlatPageEditorView;
import lombok.Getter;

public class FlatPageController extends AbstractPageController {

  @Getter
  private final NeoFlatPageEditorView view;
  @Getter
  private final FlatPageModel model;

  public FlatPageController(FlatPageModel pageModel, NeoFlatPageEditorView pageView) {
    this.model = pageModel;
    this.view = pageView;
    initView();
  }
}
