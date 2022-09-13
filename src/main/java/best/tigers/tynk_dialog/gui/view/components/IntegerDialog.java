package best.tigers.tynk_dialog.gui.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerDialog {
  private final JDialog dialog;
  private int value;
  private final JTextField intField;
  private final JButton okayButton;

  public IntegerDialog() {
    value = 0;
    intField = new JTextField();
    dialog = new JDialog();
    dialog.setModal(true);
    okayButton = new JButton("OK (Enter)");
  }

  public static int promptForInteger() {
    var prompt = new IntegerDialog();
    prompt.init();
    return prompt.getValue();
  }

  public int getValue() {
    return value;
  }

  public void setValue(int newValue) {
    value = newValue;
  }

  private void init() {
    setupIntegerField();
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
    intField.requestFocus();
  }

  private void setupInnerPanel() {
    var inner = new JPanel();
    var layout = new GroupLayout(inner);

    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    var promptLabel = new JLabel("Enter a number for the delay");
    var inputIcon = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));

    inner.setBorder(new EmptyBorder(4, 4, 4, 4));
    inner.setLayout(layout);

    var spacer = new JPanel();
    layout.setHorizontalGroup(
            layout
                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(
                            layout
                                    .createSequentialGroup()
                                    .addComponent(inputIcon, 60, 60, 60)
                                    .addGroup(
                                            layout
                                                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                                                    .addComponent(promptLabel)
                                                    .addComponent(intField))
                                    .addComponent(spacer, 30, 30, 30))
                    .addComponent(okayButton));
    layout.setVerticalGroup(
            layout
                    .createSequentialGroup()
                    .addGroup(layout.createSequentialGroup().addComponent(promptLabel))
                    .addGroup(
                            layout
                                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(inputIcon, 60, 60, 60)
                                    .addComponent(
                                            intField,
                                            intField.getPreferredSize().height,
                                            intField.getPreferredSize().height,
                                            intField.getPreferredSize().height)
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
        var valueString = intField.getText();
        if (valueString != null && !valueString.equals("") && Integer.parseInt(valueString) > 0) {
          setValue(Integer.parseInt(valueString));
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

  private void setupIntegerField() {
    ((AbstractDocument) intField.getDocument())
            .setDocumentFilter(
                    new DocumentFilter() {
                      final Pattern regEx = Pattern.compile("\\d*");

                      @Override
                      public void replace(
                              FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
                        Matcher matcher = regEx.matcher(text);
                        if (!matcher.matches()) {
                          return;
                        }
                        try {
                          super.replace(fb, offset, length, text, attrs);
                        } catch (BadLocationException e) {
                          e.printStackTrace();
                        }
                      }
                    });
  }
}
