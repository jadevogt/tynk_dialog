package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.TalkPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TalkPageController extends AbstractPageController {
  private final TalkPageEditorView view;
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

  @Override
  public void saveChanges() {
    javax.swing.ToolTipManager.sharedInstance().setInitialDelay(1000);
    String newSpeaker = view.getSpeaker();
    String newContent = view.getContent();
    String newBlip = view.getBlip();
    String newStyle = view.getStyle();
    boolean blipEnabled = view.getBlipEnabled();
    boolean styleEnabled = view.getStyleEnabled();
    if (!model.getSpeaker().equals(newSpeaker)) {
      model.setSpeaker(newSpeaker);
    }
    if (!model.getContent().equals(newContent)) {
      model.setContent(newContent);
    }
    if (model.getBlipEnabled() != blipEnabled) {
      model.setBlipEnabled(blipEnabled);
    }
    if (model.getBlipEnabled()) {
      model.setBlip(newBlip);
    }
    if (model.getStyleEnabled() != styleEnabled) {
      model.setStyleEnabled(styleEnabled);
    }
    if (model.getStyleEnabled()) {
      model.setTextBoxStyle(newStyle);
    }
  }
}
