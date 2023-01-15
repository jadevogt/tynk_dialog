package best.tigers.tynkdialog.gui.view.components;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuickPair extends JPanel {

  public QuickPair(String label, String value) {
    super();
    var layout = new BoxLayout(this, BoxLayout.X_AXIS);
    setLayout(layout);
    var boldLabel = new JLabel(label + ": ");
    boldLabel.setFont(this.getFont().deriveFont(Font.BOLD, 16));
    add(boldLabel, LEFT_ALIGNMENT);
    var strValue = value == null ? "None" : value;
    var valueLabel = new JLabel(strValue);
    valueLabel.setFont(this.getFont().deriveFont(Font.PLAIN, 16));
    add(valueLabel, LEFT_ALIGNMENT);
    setMaximumSize(new Dimension(999, 18));
    setBackground(null);
    boldLabel.setBackground(null);
    valueLabel.setBackground(null);
  }
}
