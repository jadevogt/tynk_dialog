package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.view.DialogPageEditorView;

public class DialogPageController {
  private final DialogPageEditorView view;
  private DialogPageModel model;

  public DialogPageController(DialogPageModel model, boolean trans) {
    this(model);
  }

  public DialogPageController(DialogPageModel model) {
    view = new DialogPageEditorView(model).init();
    view.makeTrans();
    view.attachSaveFunction(e -> {
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
    });
  }

  public DialogPageController() {
    this(new DialogPageModel());
  }
}
