package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.ChoiceResponse.ResponseIcon;
import best.tigers.tynkdialog.supertext.SuperTextDocument;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ChoiceResponseDialog {

  private final JDialog dialog;
  private final SuperTextDisplayPane textField;
  private final JTextField resultField;
  private final JComboBox<ChoiceResponse.ResponseIcon> iconSelector;
  private final JButton okayButton;
  private String content;
  private String result;
  private ResponseIcon icon;
  private JToolBar toolBar;

  public ChoiceResponseDialog(ChoiceResponse response) {
    textField = new SuperTextDisplayPane(1);
    textField.setLighterBackground();
    textField.setText(response.getContent());
    resultField = new JTextField();
    resultField.setText(response.getChoiceResult());
    iconSelector = new JComboBox<>();
    iconSelector.addItem(ResponseIcon.ACT);
    iconSelector.addItem(ResponseIcon.NEUTRAL);
    iconSelector.addItem(ResponseIcon.CANCEL);
    iconSelector.addItem(ResponseIcon.GIFT);
    iconSelector.addItem(ResponseIcon.SPEAK);
    iconSelector.setSelectedItem(response.getIcon());
    toolBar = createContentToolbar();
    dialog = new DynamicDialog();
    dialog.setModal(true);
    okayButton = new JButton("OK (Enter)");
  }

  protected JToolBar createContentToolbar() {
    var toolbar = new JToolBar();
    var cf = textField;
    var map = cf.getActionMap();
    var function = new JButton(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = FunctionCallDialog.promptForFunctionDetails();
        SuperTextEditorKit.addFunctionCall(cf, details[0], details[1]);
      }
    });
    function.setText("Function call...");
    var actions = new Object[]{
        SuperTextEditorKit.TYNK_RED_TEXT,
        SuperTextEditorKit.TYNK_YELLOW_TEXT,
        SuperTextEditorKit.TYNK_BLUE_TEXT,
        SuperTextEditorKit.TYNK_GREEN_TEXT,
        SuperTextEditorKit.TYNK_GREY_TEXT,
        SuperTextEditorKit.TYNK_WHITE_TEXT,
        SuperTextEditorKit.DELAY_ACTION_FIVE,
        SuperTextEditorKit.DELAY_ACTION_FIFTEEN,
        SuperTextEditorKit.DELAY_ACTION_SIXTY
    };
    Arrays.stream(actions).forEach(action -> toolbar.add(map.get(action)));
    toolbar.add(function);
    return toolbar;
  }

  public ChoiceResponseDialog() {
    this(new ChoiceResponse());
  }

  public static ChoiceResponse promptForResponseDetails() {
    var prompt = new ChoiceResponseDialog();
    prompt.init();
    return prompt.getValue();
  }

  public static ChoiceResponse promptForResponseDetails(ChoiceResponse response) {
    var prompt = new ChoiceResponseDialog(response);
    prompt.init();
    return prompt.getValue();
  }

  public ChoiceResponse getValue() {
    return new ChoiceResponse(content, result, icon);
  }

  public void setValue(ChoiceResponse response) {
    content = response.getContent();
    result = response.getChoiceResult();
    icon = response.getIcon();
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
    textField.requestFocus();
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
            .createParallelGroup(Alignment.CENTER)
            .addComponent(toolBar)
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
                    .addComponent(textField)
                    .addComponent(resultField)
                ))
            .addComponent(iconSelector)
            .addComponent(spacer, 30, 30, 30)
            .addComponent(okayButton));
    layout.setVerticalGroup(
        layout
            .createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(inputIcon)
                .addComponent(promptLabel)
            )
            .addComponent(toolBar)
            .addGroup(
                layout
                    .createParallelGroup(Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.CENTER)
                            .addComponent(nameLabel)
                            .addComponent(
                                textField,
                                textField.getPreferredSize().height,
                                textField.getPreferredSize().height,
                                textField.getPreferredSize().height))
                        .addGroup(layout.createParallelGroup(Alignment.CENTER)
                            .addComponent(paramLabel)
                            .addComponent(
                                resultField,
                                resultField.getPreferredSize().height,
                                resultField.getPreferredSize().height,
                                resultField.getPreferredSize().height))
                        .addComponent(iconSelector))
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
        var textString = textField.getText();
        var resultString = resultField.getText();
        var icon = iconSelector.getItemAt(iconSelector.getSelectedIndex());
        if (textString != null && !textString.equals("")) {
          setValue(new ChoiceResponse(textString, resultString, icon));
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
