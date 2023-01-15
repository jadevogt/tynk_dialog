package best.tigers.tynkdialog.gui.controller.page;

import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public abstract class AbstractPageController {

  abstract AbstractPageEditorView getView();

  public void setupViewShortcuts() {
    var enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK, true);
    var enterMapKey = "Shift+Enter released";
    getView().attachFunctionalKeyboardShortcut(enterKey, enterMapKey, this::saveAndExit);

    var enterCtrlKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, true);
    var enterCtrlMapKey = "Ctrl+Enter released";
    getView().attachKeyboardShortcut(enterCtrlKey, enterCtrlMapKey, getView().getContinueAction());
  }

  public abstract void saveChanges();

  void initView() {
    getView().attachSaveAction(this::saveAndExit);
    getView().init();
  }

  public void saveAndExit() {
    this.saveChanges();
    getView().close();
  }


}
