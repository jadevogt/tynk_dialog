package best.tigers.tynkdialog.gui.view;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public interface ShortcutSupport {

  void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, Runnable action);

  void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, AbstractAction action);
}
