package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.FlatPageEditorView;
import lombok.Getter;

public class FlatPageController extends AbstractPageController {
  @Getter
  private final FlatPageEditorView view;
  @Getter
  private final FlatPageModel model;

  public FlatPageController(FlatPageModel pageModel, FlatPageEditorView pageView) {
    this.model = pageModel;
    this.view = pageView;
    initView();
  }
}
