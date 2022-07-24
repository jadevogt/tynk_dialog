package best.tigers.tynk_dialog.gui.controller;

import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.view.DialogPageEditorView;
import best.tigers.tynk_dialog.gui.view.DialogPageView;

import javax.swing.*;

public class DialogPageEditorController {
    private DialogPageEditorView view;
    private DialogPageModel model;

    public DialogPageEditorController(DialogPageModel model, boolean trans) {
        this(model);
        view.makeTrans();
    }

    public DialogPageEditorController(DialogPageModel model) {
        view = new DialogPageEditorView(model);
        view.init();
        view.attachSaveFunction(e -> {
            var newSpeaker = view.getSpeaker();
            var newContent = view.getContent();
            model.setSpeaker(newSpeaker);
            model.setContent(newContent);
        });
        view.update();
    }
    public DialogPageEditorController() {
        this(new DialogPageModel());
    }
}
