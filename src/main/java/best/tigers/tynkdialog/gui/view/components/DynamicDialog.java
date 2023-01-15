package best.tigers.tynkdialog.gui.view.components;

import javax.swing.JDialog;

public class DynamicDialog extends JDialog implements DynamicUI {

  public DynamicDialog() {
    super();
    installListeners();
  }

}
