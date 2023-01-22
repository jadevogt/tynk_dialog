package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.model.page.FlatPageModel;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.gui.view.page.FlatPageEditorView;
import lombok.Getter;

public class ChoicePageController extends AbstractPageController {
  @Getter
  private final ChoicePageModel model;

  @Getter
  private final AbstractPageEditorView view;

  public ChoicePageController(ChoicePageModel model, AbstractPageEditorView view) {
    this.model = model;
    this.view = view;
    initView();
  }
}
