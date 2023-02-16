package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.page.ChoicePageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import lombok.Getter;

public class ChoicePageController extends AbstractPageController {

  @Getter
  private final ChoicePageModel model;

  @Getter
  private final ChoicePageEditorView view;

  public ChoicePageController(ChoicePageModel model, ChoicePageEditorView view) {
    this.model = model;
    this.view = view;
    initView();

  }

  @Override
  public void setupViewShortcuts() {
    super.setupViewShortcuts();
    SuperTextEditorKit.getCannedDelayActions()
            .forEach(a -> view.attachKeyboardShortcut(a.getKeyStroke(), a.getKeyMapName(), a));
    SuperTextEditorKit.getBehaviorActions()
            .forEach(a -> view.attachKeyboardShortcut(a.getKeyStroke(), a.getKeyMapName(), a));
    SuperTextEditorKit.getColorActions()
            .forEach(a -> view.attachKeyboardShortcut(a.getKeyStroke(), a.getKeyMapName(), a));
  }


}
