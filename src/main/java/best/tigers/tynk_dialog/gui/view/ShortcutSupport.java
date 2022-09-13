package best.tigers.tynk_dialog.gui.view;

import javax.swing.*;

public interface ShortcutSupport {
  void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, Runnable action);
  void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, AbstractAction action);
}
