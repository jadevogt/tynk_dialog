package best.tigers.tynk_dialog.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public final class CommonDialogs {
  private CommonDialogs() {}
  ;

  public static class IntegerDialog {
    private final JDialog dialog;
    private int value;

    public IntegerDialog() {
      value = 0;
      dialog = new JDialog();
      dialog.setModal(true);
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
      var intField = new JTextField();
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
      var okayButton = new JButton("OK");
      var doneAction =
          new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
              var value = intField.getText();
              if (value != null && Integer.parseInt(value) > 0) {
                setValue(Integer.parseInt(value));
                dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
              } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                intField.requestFocus();
              }
            }
          };
      String enterKey = "EnterKey";
      intField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterKey);
      intField.getActionMap().put(enterKey, doneAction);
      ((JPanel) dialog.getContentPane())
          .getInputMap()
          .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterKey);
      ((JPanel) dialog.getContentPane()).getActionMap().put(enterKey, doneAction);
      okayButton.addActionListener(doneAction);
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
                              .createParallelGroup(Alignment.LEADING)
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
                      .createParallelGroup(Alignment.CENTER)
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
      dialog.setVisible(true);
      intField.requestFocus();
    }
  }
}
