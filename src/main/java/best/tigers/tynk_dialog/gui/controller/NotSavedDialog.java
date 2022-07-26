package best.tigers.tynk_dialog.gui.controller;

import javax.swing.*;

public class NotSavedDialog {
  private JDialog dialog;
  public enum Decision {
    SAVE_IN_PLACE,
    SAVE_AS,
    CONTINUE_WITHOUT_SAVING,
    CANCEL_OPERATION
  }

  public NotSavedDialog() {
    dialog = new JDialog();
    dialog.setModal(true);
    JLabel message = new JLabel("Your changes have not been saved to disk. What would you like to do?");
    JButton cancelButton = new JButton("Cancel");
    JButton saveButton = new JButton("Save");
    JButton saveAsButton = new JButton("Save As");
    JButton continueButton = new JButton("Continue without saving");
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(cancelButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(saveAsButton);
    buttonPanel.add(continueButton);
    mainPanel.add(message);
    mainPanel.add(buttonPanel);
    dialog.add(mainPanel);
  }

  public Decision show() {
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
    return Decision.CANCEL_OPERATION;
  }
}
