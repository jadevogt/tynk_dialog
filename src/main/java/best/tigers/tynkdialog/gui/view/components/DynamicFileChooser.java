package best.tigers.tynkdialog.gui.view.components;

import javax.swing.JFileChooser;

public class DynamicFileChooser extends JFileChooser implements DynamicUI {

  public DynamicFileChooser() {
    super();
    installListeners();
  }
}
