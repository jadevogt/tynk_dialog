package best.tigers.tynkdialog.gui.view.page.neo;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.gui.view.components.neo.NeoFunctionCallDialog;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import best.tigers.tynkdialog.util.PredictiveTextService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyleContext;

public class NeoTalkPageEditorView extends AbstractPageEditorView {

  private JPanel rootPanel;
  private JComboBox<String> character;
  private JCheckBox blipCheck;
  private TalkPageModel model;
  private JComboBox<String> blip;
  private JComboBox<String> style;
  private JCheckBox styleCheck;
  private JButton saveButton;
  private JButton createAnotherButton;
  private JCheckBox skipCheck;
  private JEditorPane contentField;
  private JToolBar contentToolbar;
  private JLabel blipLabel;
  private JLabel styleLabel;
  private JLabel skipLabel;
  private JLabel skipWarningLabel;

  public NeoTalkPageEditorView(TalkPageModel model) {
    super();
    this.model = model;
  }

  public void init() {
    super.init();
    getFrame().add(rootPanel);
    initContentField();
    initContentToolbar();
    blipCheck.addActionListener(e -> blip.setEnabled(blipCheck.isSelected()));
    blipLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        blipCheck.doClick();
      }
    });
    styleCheck.addActionListener(e -> style.setEnabled(styleCheck.isSelected()));
    styleLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        styleCheck.doClick();
      }
    });

    PredictiveTextService.getInstance()
            .getCollectionTerms("characters")
            .stream()
            .limit(5)
            .forEachOrdered(x -> character.addItem(x.getKey()));
    PredictiveTextService.getInstance()
            .getCollectionTerms("blips")
            .stream()
            .limit(5)
            .forEachOrdered(x -> blip.addItem(x.getKey()));
    PredictiveTextService.getInstance()
            .getCollectionTerms("textboxes")
            .stream()
            .limit(5)
            .forEachOrdered(x -> style.addItem(x.getKey()));
    getFrame().pack();
    getFrame().setJMenuBar(createContentMenubar());
    getFrame().pack();
    getFrame().setMinimumSize(getFrame().getSize());
  }

  @Override
  public TalkPageModel getModel() {
    return model;
  }

  @Override
  public void update() {
    super.update();
    getFrame().setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
    var isBlipEnabled = model.getBlip() != null;
    var isStyleEnabled = model.getTextStyle() != null;
    blip.setEnabled(isBlipEnabled);
    style.setEnabled(isStyleEnabled);
    blipCheck.setSelected(isBlipEnabled);
    styleCheck.setSelected(isStyleEnabled);
    character.setSelectedItem(model.getSpeaker());
    contentField.setText(model.getContent());
    if (isBlipEnabled) {
      blip.setSelectedItem(model.getBlip());
    }
    if (isStyleEnabled) {
      style.setSelectedItem(model.getTextStyle());
    }
    skipCheck.setSelected(model.isCanSkip());
  }

  public TalkPage asPage() {
    var blipValue = (String) blip.getSelectedItem();
    if (!blipCheck.isSelected()) {
      blipValue = null;
    }
    var styleValue = (String) style.getSelectedItem();
    if (!styleCheck.isSelected()) {
      styleValue = null;
    }
    return new TalkPage((String) character.getSelectedItem(), contentField.getText(), styleValue, blipValue,
            skipCheck.isSelected());
  }

  private void initContentField() {

  }

  public JEditorPane getContentField() {
    return contentField;
  }

  public SuperTextEditorKit getEditorKit() {
    var kit = contentField.getEditorKit();
    return (SuperTextEditorKit) kit;
  }

  protected void initContentToolbar() {
    var toolbar = contentToolbar;
    var cf = getContentField();
    var map = cf.getActionMap();
    var function = new JButton(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = NeoFunctionCallDialog.promptForFunctionDetails();
        SuperTextEditorKit.addFunctionCall(cf, details[0], details[1]);
        autoDisableSkip();
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
  }

  protected JMenuBar createContentMenubar() {
    var menubar = new JMenuBar();
    var cf = getContentField();
    var editMenu = new JMenu("Edit");
    var insertMenu = new JMenu("Insert");
    var colorMenu = new JMenu("Colors");
    var behaviorMenu = new JMenu("Behaviors");

    var kit = getEditorKit();
    SuperTextEditorKit.getColorActions().forEach(colorMenu::add);
    SuperTextEditorKit.getBehaviorActions().forEach(behaviorMenu::add);
    var delay = new JMenuItem(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int magnitude = IntegerDialog.promptForInteger();
        SuperTextEditorKit.addTimeDelay(cf, magnitude);
      }
    });
    delay.setText("Delay...");
    var function = new JMenuItem(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = NeoFunctionCallDialog.promptForFunctionDetails();
        SuperTextEditorKit.addFunctionCall(cf, details[0], details[1]);
        autoDisableSkip();
      }
    });
    function.setText("Function call...");
    insertMenu.add(delay);
    insertMenu.add(function);

    var cut = new DefaultEditorKit.CutAction();
    cut.putValue(Action.NAME, "Cut");
    editMenu.add(cut);

    var copy = new DefaultEditorKit.CopyAction();
    copy.putValue(Action.NAME, "Copy");
    editMenu.add(copy);

    var paste = new DefaultEditorKit.PasteAction();
    paste.putValue(Action.NAME, "Paste");
    editMenu.add(paste);

    menubar.add(editMenu);
    menubar.add(insertMenu);
    menubar.add(colorMenu);
    menubar.add(behaviorMenu);
    return menubar;
  }

  public void autoDisableSkip() {
    skipCheck.setSelected(false);
    skipWarningLabel.setText(" (auto-unchecked due to function call)");
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
    createAnotherButton.addActionListener(getContinueAction());
  }

  private void createUIComponents() {
    contentField = new JEditorPane();
    contentField.setMargin(new Insets(0, 0, 0, 0));
    contentField.setFont(Assets.getTerminus());
    contentField.setForeground(Constants.TextColor.WHITE.toAWT());
    contentField.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    contentField.setContentType("text/supertext");
    contentField.setEditorKit(new SuperTextEditorKit());
    contentField.setPreferredSize(new Dimension(600, 100));
    saveButton = new JButton();
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    rootPanel = new JPanel();
    rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(10, 10, 10, 10), -1, -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    rootPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    saveButton.setText("Save Changes (Shift + Enter)");
    panel1.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    createAnotherButton = new JButton();
    createAnotherButton.setText("Create Next Texbox (Ctrl + Enter)");
    panel1.add(createAnotherButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
    rootPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    panel2.add(contentField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(600, 100), null, 0, false));
    contentToolbar = new JToolBar();
    panel2.add(contentToolbar, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    rootPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("Speaker");
    panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    character = new JComboBox();
    character.setEditable(true);
    panel3.add(character, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1, false, true));
    rootPanel.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 3, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    blipCheck = new JCheckBox();
    blipCheck.setText("");
    panel4.add(blipCheck, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    blip = new JComboBox();
    blip.setEditable(true);
    panel4.add(blip, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    style = new JComboBox();
    style.setEditable(true);
    panel4.add(style, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    blipLabel = new JLabel();
    blipLabel.setText("Blip");
    panel4.add(blipLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    styleLabel = new JLabel();
    styleLabel.setText("Textbox Style");
    panel4.add(styleLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    styleCheck = new JCheckBox();
    styleCheck.setText("");
    panel4.add(styleCheck, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    skipCheck = new JCheckBox();
    skipCheck.setText("");
    panel4.add(skipCheck, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    panel4.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    skipLabel = new JLabel();
    skipLabel.setText("Skippable");
    panel5.add(skipLabel);
    skipWarningLabel = new JLabel();
    Font skipWarningLabelFont = this.$$$getFont$$$(null, Font.ITALIC, -1, skipWarningLabel.getFont());
    if (skipWarningLabelFont != null) skipWarningLabel.setFont(skipWarningLabelFont);
    skipWarningLabel.setText("");
    panel5.add(skipWarningLabel);
  }

  /**
   * @noinspection ALL
   */
  private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
    if (currentFont == null) return null;
    String resultName;
    if (fontName == null) {
      resultName = currentFont.getName();
    } else {
      Font testFont = new Font(fontName, Font.PLAIN, 10);
      if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
        resultName = fontName;
      } else {
        resultName = currentFont.getName();
      }
    }
    Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
    Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
    return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
  }
}
