package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.neo.NeoTalkPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import lombok.Getter;

public class TalkPageController extends AbstractPageController {

  private final NeoTalkPageEditorView view;
  @Getter
  private final TalkPageModel model;

  public TalkPageController(TalkPageModel model, NeoTalkPageEditorView view) {
    this.model = model;
    this.view = view;
    initView();
  }

  @Override
  NeoTalkPageEditorView getView() {
    return view;
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
