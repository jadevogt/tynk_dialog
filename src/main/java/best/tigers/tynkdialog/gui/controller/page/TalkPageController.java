package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.neo.NeoTalkPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.PredictiveTextService;
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
  public void saveChanges() {
    super.saveChanges();
    PredictiveTextService.getInstance().incrementTerm("characters", model.getSpeaker());
    if (model.getBlip() != null) {
      PredictiveTextService.getInstance().incrementTerm("blips", model.getBlip());
    }
    if (model.getTextStyle() != null) {
      PredictiveTextService.getInstance().incrementTerm("textboxes", model.getTextStyle());
    }
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
