package best.tigers.tynkdialog.gui.view.components;

import javax.swing.JLabel;
import javax.swing.JTextField;
import lombok.Getter;

public class LabeledField {
  @Getter
  private final JLabel label;
  @Getter
  private final JTextField field;

  public LabeledField(String labelText) {
    label = new JLabel(labelText);
    field = new JTextField(40);
    label.setHorizontalAlignment(JLabel.LEFT);
  }

  public String getText() {
    return field.getText();
  }

  public void setText(String newText) {
    field.setText(newText);
  }

  public void setEnabled(boolean enabled) {
    field.setEnabled(enabled);
  }

  public int getHeight() {
    return field.getPreferredSize().height;
  }

  public int getWidth() {
    return field.getPreferredSize().width;
  }
}
