package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.TalkPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import lombok.Getter;

public class TalkPageController extends AbstractPageController {

  private final TalkPageEditorView view;
  @Getter
  private final TalkPageModel model;

  public TalkPageController(TalkPageModel model, TalkPageEditorView view) {
    this.model = model;
    this.view = view;
    initView();
  }

  @Override
  TalkPageEditorView getView() {
    return view;
  }

  @Override
  public void setupViewShortcuts() {
    super.setupViewShortcuts();

    var oneKey = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK, true);
    var oneMapKey = "One+Enter released";
    view.attachKeyboardShortcut(oneKey, oneMapKey, SuperTextEditorKit.DELAY_ACTION_FIVE);

    var twoKey = KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK, true);
    var twoMapKey = "Two+Enter released";
    view.attachKeyboardShortcut(twoKey, twoMapKey, SuperTextEditorKit.DELAY_ACTION_FIFTEEN);

    var threeKey = KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK, true);
    var threeMapKey = "Three+Enter released";
    view.attachKeyboardShortcut(threeKey, threeMapKey, SuperTextEditorKit.DELAY_ACTION_SIXTY);

    view.getEditorKit().getColorActions().forEach(a -> {
      var keyStroke = a.getShortcutKey();
      var keyMapName = a.getKeyMapName();
      view.attachKeyboardShortcut(keyStroke, keyMapName, a);
    });

    view.getEditorKit().getBehaviorActions().forEach(a -> {
      var keystroke = a.getShortcutKey();
      var keyMapName = a.getKeyMapName();
      view.attachKeyboardShortcut(keystroke, keyMapName, a);
    });
  }
}
