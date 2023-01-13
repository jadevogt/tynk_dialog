package best.tigers.tynkdialog.gui.controller.page;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.page.TalkPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.KeyStroke;

public class TalkPageController implements PageController {
  private final TalkPageEditorView view;
  private final TalkPageModel model;

  TalkPageController(TalkPageModel model, boolean proceeding) {
    this.model = model;
    if (proceeding) {
      view = TalkPageEditorView.fromModelProceeding(model).init();
    } else {
      view = new TalkPageEditorView(model).init();
    }
  }

  TalkPageController(TalkPageModel model) {
    this(model, false);
  }

  public TalkPageControllerFactory getFactory() {return new TalkPageControllerFactory();}

  public static TalkPageController fromModel(TalkPageModel model) {
    var controller = new TalkPageController(model);
    controller.setupViewShortcuts();
    return controller;
  }

  public static void editModel(TalkPageModel model) {
    TalkPageController.fromModel(model);
  }

  public TalkPageModel getModel() {
    return this.model;
  }

  public TalkPageEditorView getView() {
    return view;
  }

  @Override
  public void setupViewShortcuts() {
    var enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK, true);
    var enterMapKey = "Shift+Enter released";

    view.attachFunctionalKeyboardShortcut(enterKey, enterMapKey, this::saveAndExit);
    view.attachSaveAction(this::saveAndExit);

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

  @Override
  public void saveAndExit() {
    saveChanges();
    view.getPanel().dispatchEvent(new WindowEvent(view.getFrame(), WINDOW_CLOSING));
    view.getFrame().dispose();
  }
}
