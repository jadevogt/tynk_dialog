package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.view.DialogPageEditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DialogPageController {
  private final DialogPageEditorView view;
  private DialogPageModel model;

  private DialogPageController(DialogPageModel model) {
    this.model = model;
    view = new DialogPageEditorView(model).init();
    view.getPanel().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(
                            KeyEvent.VK_ENTER,
                            InputEvent.SHIFT_DOWN_MASK,
                            true),
                    "Shift+Enter released");
    view.getPanel().getActionMap().put("Shift+Enter released", new SaveAction());
    view.attachSaveFunction(new SaveAction());
  }

  public static DialogPageController fromModel(DialogPageModel model) {
    return new DialogPageController(model);
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

  class SaveAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
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
}
