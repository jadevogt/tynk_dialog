package best.tigers.tynkdialog.gui.controller;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

import best.tigers.tynkdialog.gui.model.DialogPageModel;
import best.tigers.tynkdialog.gui.text.SuperTextEditorKit;
import best.tigers.tynkdialog.gui.view.DialogPageEditorView;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.KeyStroke;

public class DialogPageController {

  private final DialogPageEditorView view;
  private final DialogPageModel model;

  private DialogPageController(DialogPageModel model, boolean proceeding) {
    this.model = model;
    if (proceeding) {
      view = DialogPageEditorView.fromModelProceeding(model).init();
    } else {
      view = new DialogPageEditorView(model).init();
    }
  }

  private DialogPageController(DialogPageModel model) {
    this(model, false);
  }

  public static DialogPageController fromModel(DialogPageModel model) {
    var controller = new DialogPageController(model);
    controller.setupViewShortcuts();
    return controller;
  }

  public static DialogPageController fromModelProceeding(DialogPageModel model) {
    var controller = new DialogPageController(model, true);
    controller.setupViewShortcuts();
    return controller;
  }

  public static DialogPageController fromNewModel() {
    return DialogPageController.fromModel(new DialogPageModel());
  }

  public static void editModel(DialogPageModel model) {
    DialogPageController.fromModel(model);
  }

  public static DialogPageModel createModel() {
    var model = new DialogPageModel();
    DialogPageController.editModel(model);
    return model;
  }

  public DialogPageEditorView getView() {
    return view;
  }

  private void setupViewShortcuts() {
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

  private void saveChanges() {
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

  public void saveAndExit() {
    saveChanges();
    view.getPanel().dispatchEvent(new WindowEvent(view.getFrame(), WINDOW_CLOSING));
    view.getFrame().dispose();
  }
}
