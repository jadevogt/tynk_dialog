package best.tigers.tynk_dialog.gui.view.components;

import javax.swing.border.EtchedBorder;

public class Misc {
  public static javax.swing.border.TitledBorder getBorder(String title) {
    EtchedBorder tempBorder = new EtchedBorder();
    javax.swing.border.TitledBorder border = new javax.swing.border.TitledBorder(tempBorder, title);
    border.setTitlePosition(javax.swing.border.TitledBorder.TOP);
    return border;
  }
}
