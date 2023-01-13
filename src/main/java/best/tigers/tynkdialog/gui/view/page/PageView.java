package best.tigers.tynkdialog.gui.view.page;

import javax.swing.*;

public interface PageView {
  void attachSaveAction(Runnable action);
  void attachContinueAction(Runnable action);

  JPanel getPanel();

  JFrame getFrame();
}
