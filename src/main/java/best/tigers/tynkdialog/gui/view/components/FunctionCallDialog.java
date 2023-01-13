package best.tigers.tynkdialog.gui.view.components;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class FunctionCallDialog {

  private final JDialog dialog;
  private final JTextField nameField;
  private final JTextField paramField;
  private final JButton okayButton;
  private String name;
  private String param;

  public FunctionCallDialog(String functionName, String functionParam) {
    nameField = new JTextField();
    nameField.setText(functionName);
    paramField = new JTextField();
    paramField.setText(functionParam);
    dialog = new JDialog();
    dialog.setModal(true);
    okayButton = new JButton("OK (Enter)");
  }

  public FunctionCallDialog() {
    this("", "");
  }

  public static String[] promptForFunctionDetails() {
    var prompt = new FunctionCallDialog();
    prompt.init();
    return prompt.getValue();
  }

  public static String[] promptForFunctionDetails(String functionName, String functionParams) {
    var prompt = new FunctionCallDialog(functionName, functionParams);
    prompt.init();
    return prompt.getValue();
  }

  public String[] getValue() {
    return new String[] { name, param };
  }

  public void setValue(String[] newDetails) {
    name = newDetails[0];
    param = newDetails[1];
  }

  public void setValue(String newName, String newParam) {
    name = newName;
    param = newParam;
  }

  private void init() {
    // apply layout
    setupInnerPanel();
    var panel = (JPanel) dialog.getContentPane();
    var validateAndExitAction = createValidateAndExitAction();

    // setup keyboard shortcuts
    var enterMapKey = "EnterKey";
    var enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    var inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    var actionMap = panel.getActionMap();

    inputMap.put(enterKey, enterMapKey);
    actionMap.put(enterMapKey, validateAndExitAction);

    okayButton.addActionListener(validateAndExitAction);
    dialog.setVisible(true);
    focusInput();
  }

  private void focusInput() {
    nameField.requestFocus();
  }

  private void setupInnerPanel() {
    var inner = new JPanel();
    var layout = new GroupLayout(inner);

    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    var promptLabel = new JLabel("Enter the function details:");
    var nameLabel = new JLabel("Name:");
    var paramLabel = new JLabel("Argument:");
    var inputIcon = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));

    inner.setBorder(new EmptyBorder(4, 4, 4, 4));
    inner.setLayout(layout);

    var spacer = new JPanel();
    layout.setHorizontalGroup(
            layout
                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(inputIcon)
                            .addComponent(promptLabel)
                    )
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                    .addComponent(nameLabel)
                                    .addComponent(paramLabel)
                            )
                            .addGroup(layout.createParallelGroup()
                                    .addComponent(nameField)
                                    .addComponent(paramField)
                            ))
                    .addComponent(spacer, 30, 30, 30)
                    .addComponent(okayButton));
    layout.setVerticalGroup(
            layout
                    .createSequentialGroup()
                    .addGroup(layout.createParallelGroup(Alignment.CENTER)
                            .addComponent(inputIcon)
                            .addComponent(promptLabel)
                    )
                    .addGroup(
                            layout
                                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                                                    .addComponent(nameLabel)
                                                    .addComponent(
                                                            nameField,
                                                            nameField.getPreferredSize().height,
                                                            nameField.getPreferredSize().height,
                                                            nameField.getPreferredSize().height))
                                            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                                                    .addComponent(paramLabel)
                                                    .addComponent(
                                                            paramField,
                                                            paramField.getPreferredSize().height,
                                                            paramField.getPreferredSize().height,
                                                            paramField.getPreferredSize().height)))
                                    .addComponent(spacer, 30, 30, 30))
                    .addComponent(okayButton));

    inner.setSize(new Dimension(400, 240));
    dialog.setLayout(new BorderLayout());
    dialog.add(inner, BorderLayout.CENTER);
    dialog.pack();
    dialog.setLocationRelativeTo(dialog.getParent());
  }

  private AbstractAction createValidateAndExitAction() {
    return new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        var nameString = nameField.getText();
        var paramString = paramField.getText();
        if (nameString != null && !nameString.equals("")) {
          setValue(nameString, paramString);
          closeDialog();
        } else {
          java.awt.Toolkit.getDefaultToolkit().beep();
          focusInput();
        }
      }
    };
  }

  private void closeDialog() {
    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
  }
}
