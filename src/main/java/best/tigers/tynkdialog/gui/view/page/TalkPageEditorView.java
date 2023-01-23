package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
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
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

public class TalkPageEditorView extends AbstractPageEditorView {

  private final TalkPageModel model;
  private final LabeledField character;
  private final JLabel contentLabel;
  private final JEditorPane contentField;
  private final JToolBar contentToolbar;
  private final JButton saveButton;
  private final JButton createAnotherButton;
  private final JCheckBox blipCheck;
  private final LabeledField blip;
  private final JCheckBox styleCheck;
  private final LabeledField style;
  private final JLabel skipWarningLabel;
  private final JPanel skipLabelPanel;
  private final JCheckBox skipCheck;

  public TalkPageEditorView(TalkPageModel model) {
    super();
    this.model = model;
    character = new LabeledField("Character");

    contentLabel = new JLabel("Content");
    contentField = createContentField();
    contentToolbar = createContentToolbar();
    blip = new LabeledField("Blip");
    blipCheck = new JCheckBox();
    style = new LabeledField("Style");
    styleCheck = new JCheckBox();
    JLabel skipLabel = createLabel("Skippable");
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
    var layout = createGroupLayout();
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(character.getLabel())
                    .addComponent(contentLabel)
                    .addComponent(blip.getLabel())
                    .addComponent(style.getLabel())
                    .addComponent(skipCheck))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(character.getField())
                    .addComponent(contentToolbar)
                    .addComponent(contentField, GroupLayout.Alignment.CENTER,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width)
                    .addComponent(blip.getField())
                    .addComponent(style.getField())
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
                .addComponent(character.getLabel())
                .addComponent(character.getField(), GroupLayout.Alignment.CENTER,
                    character.getHeight(),
                    character.getHeight(),
                    character.getHeight()))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(contentToolbar))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(contentLabel)
                .addComponent(contentField, GroupLayout.Alignment.CENTER,
                    contentField.getPreferredSize().height, contentField.getPreferredSize().height,
                    contentField.getPreferredSize().height))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(blipCheck)
                .addComponent(blip.getLabel())
                .addComponent(blip.getField(), blip.getHeight(), blip.getHeight(),
                    blip.getHeight()))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(styleCheck)
                .addComponent(style.getLabel())
                .addComponent(style.getField(), style.getHeight(), style.getHeight(),
                    style.getHeight()))
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
    blipCheck.addActionListener(e -> blip.setEnabled(blipCheck.isSelected()));
    styleCheck.addActionListener(e -> style.setEnabled(styleCheck.isSelected()));
  }

  @Override
  TalkPageModel getModel() {
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
    character.setText(model.getSpeaker());
    contentField.setText(model.getContent());
    blip.setText(model.getBlip());
    style.setText(model.getTextStyle());
    skipCheck.setSelected(model.isCanSkip());
  }

  public TalkPage asPage() {
    var blipValue = blip.getText();
    if (!blipCheck.isSelected()) {
      blipValue = null;
    }
    var styleValue = style.getText();
    if (!styleCheck.isSelected()) {
      styleValue = null;
    }
    return new TalkPage(character.getText(), contentField.getText(), styleValue, blipValue,
        skipCheck.isSelected());
  }

  private JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(Assets.getTerminus());
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
    skipCheck.setSelected(false);
    skipWarningLabel.setText(" (auto-unchecked due to function call)");
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
    createAnotherButton.addActionListener(getContinueAction());
  }
}