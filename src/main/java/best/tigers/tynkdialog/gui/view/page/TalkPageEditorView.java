package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

public class TalkPageEditorView extends AbstractPageEditorView {

  private final TalkPageModel model;

  private final JLabel characterLabel;
  private final JTextField characterField;

  private final JLabel contentLabel;
  private final JEditorPane contentField;
  private final JToolBar contentToolbar;

  private final JButton saveButton;
  private final JButton createAnotherButton;

  private final JCheckBox blipCheck;

  private final JLabel blipLabel;
  private final JTextField blipField;

  private final JCheckBox styleCheck;

  private final JLabel styleLabel;
  private final JTextField styleField;
  private final Font font = Assets.getInstance().getFont();
  private final JLabel skipLabel;
  private final JLabel skipWarningLabel;
  private final JPanel skipLabelPanel;
  private final JCheckBox skipCheck;

  public TalkPageEditorView(TalkPageModel model) {
    super();
    this.model = model;
    characterLabel = createLabel("Character");
    characterField = createField();

    contentLabel = new JLabel("Content");
    contentField = createContentField();
    contentToolbar = createContentToolbar();

    blipLabel = createLabel("Blip");
    blipField = createField();
    blipCheck = new JCheckBox();

    styleLabel = createLabel("Textbox");
    styleField = createField();
    styleCheck = new JCheckBox();

    skipLabel = createLabel("Skippable");
    skipWarningLabel = createLabel("");
    skipLabelPanel = new JPanel();
    var boxLayout = new BoxLayout(skipLabelPanel, BoxLayout.X_AXIS);
    skipLabelPanel.setLayout(boxLayout);
    skipLabelPanel.add(skipLabel, Component.LEFT_ALIGNMENT);
    skipLabelPanel.add(skipWarningLabel, Component.LEFT_ALIGNMENT);
    skipWarningLabel.setFont(skipWarningLabel.getFont().deriveFont(Font.ITALIC));
    skipWarningLabel.setForeground(Color.decode("#888888"));
    skipCheck = new JCheckBox();

    saveButton = new JButton("Save Changes (Shift + Enter)");
    createAnotherButton = new JButton("Make Next Textbox (Ctrl + Enter)");
    getPanel().setLayout(setupLayout());
    getFrame().setJMenuBar(createContentMenubar());
  }

  private GroupLayout setupLayout() {
    var layout = createGroupLayout(getPanel());
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(characterLabel)
                    .addComponent(contentLabel)
                    .addComponent(blipLabel)
                    .addComponent(styleLabel)
                    .addComponent(skipCheck))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(characterField)
                    .addComponent(contentToolbar)
                    .addComponent(contentField, GroupLayout.Alignment.CENTER,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width)
                    .addComponent(blipField)
                    .addComponent(styleField)
                    .addComponent(skipLabelPanel, Alignment.LEADING))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(blipCheck)
                    .addComponent(styleCheck)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(saveButton)
                .addComponent(createAnotherButton)));
    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(characterLabel)
                .addComponent(characterField, GroupLayout.Alignment.CENTER,
                    characterField.getPreferredSize().height,
                    characterField.getPreferredSize().height,
                    characterField.getPreferredSize().height))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(contentToolbar))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(contentLabel)
                .addComponent(contentField, GroupLayout.Alignment.CENTER,
                    contentField.getPreferredSize().height, contentField.getPreferredSize().height,
                    contentField.getPreferredSize().height))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(blipCheck)
                .addComponent(blipLabel)
                .addComponent(blipField, blipField.getPreferredSize().height,
                    blipField.getPreferredSize().height, blipField.getPreferredSize().height))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(styleCheck)
                .addComponent(styleLabel)
                .addComponent(styleField, styleField.getPreferredSize().height,
                    styleField.getPreferredSize().height, styleField.getPreferredSize().height))
            .addGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(skipCheck)
                .addComponent(skipLabelPanel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(saveButton)
                .addComponent(createAnotherButton)
            )
    );
    return layout;
  }

  public void init() {
    super.init();
    blipCheck.addActionListener(e -> blipField.setEnabled(blipCheck.isSelected()));
    styleCheck.addActionListener(e -> styleField.setEnabled(styleCheck.isSelected()));
  }

  @Override
  TalkPageModel getModel() {
    return model;
  }

  @Override
  public void update() {
    getFrame().setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
    blipField.setEnabled(model.isBlipEnabled());
    styleField.setEnabled(model.isStyleEnabled());
    blipCheck.setSelected(model.isBlipEnabled());
    styleCheck.setSelected(model.isStyleEnabled());
    setSpeaker(model.getSpeaker());
    setContent(model.getContent());
    setBlip(model.getBlip());
    setStyle(model.getTextBoxStyle());
    setCanSkip(model.isCanSkip());
  }


  public String getSpeaker() {
    return characterField.getText();
  }

  public void setSpeaker(String newSpeaker) {
    characterField.setText(newSpeaker);
  }

  public String getContent() {
    return contentField.getText();
  }

  public void setContent(String newContent) {
    contentField.setText(newContent);
  }

  public String getBlip() {
    return blipField.getText();
  }

  public void setBlip(String newBlip) {
    blipField.setText(newBlip);
  }

  public boolean getBlipEnabled() {
    return blipCheck.isSelected();
  }

  public String getStyle() {
    return styleField.getText();
  }

  public void setStyle(String newStyle) {
    styleField.setText(newStyle);
  }

  public boolean getStyleEnabled() {
    return styleCheck.isSelected();
  }

  public boolean getCanSkip() {
    return skipCheck.isSelected();
  }

  public void setCanSkip(boolean canSkip) {
    skipCheck.setSelected(canSkip);
  }

  private JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(font);
    field.setForeground(Constants.TextColor.WHITE.toAWT());
    field.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    field.setContentType("supertext/supertext");
    field.setPreferredSize(new Dimension(600, 100));
    return field;
  }

  public JEditorPane getContentField() {
    return contentField;
  }

  public SuperTextEditorKit getEditorKit() {
    var kit = contentField.getEditorKit();
    return (SuperTextEditorKit) kit;
  }

  protected JToolBar createContentToolbar() {
    var toolbar = createToolbar();
    var cf = getContentField();
    var map = cf.getActionMap();
    var function = new JButton(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = FunctionCallDialog.promptForFunctionDetails();
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
    return toolbar;
  }

  protected JMenuBar createContentMenubar() {
    var menubar = new JMenuBar();
    var cf = getContentField();
    var editMenu = new JMenu("Edit");
    var insertMenu = new JMenu("Insert");
    var colorMenu = new JMenu("Colors");
    var behaviorMenu = new JMenu("Behaviors");

    var kit = getEditorKit();
    kit.getColorActions().forEach(colorMenu::add);
    kit.getBehaviorActions().forEach(behaviorMenu::add);
    behaviorMenu.add(kit.getClearBehaviorAction());
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
        String[] details = FunctionCallDialog.promptForFunctionDetails();
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
    setCanSkip(false);
    skipWarningLabel.setText(" (auto-unchecked due to function call)");
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
    createAnotherButton.addActionListener(getContinueAction());
  }
}