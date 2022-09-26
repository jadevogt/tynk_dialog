package best.tigers.tatffdialogutility.gui.view;

import best.tigers.tatffdialogutility.game.Constants;
import best.tigers.tatffdialogutility.gui.Assets;
import best.tigers.tatffdialogutility.gui.model.DialogPageModel;
import best.tigers.tatffdialogutility.gui.text.HarlowTMLEditorKit;
import best.tigers.tatffdialogutility.gui.view.components.IntegerDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

public class DialogPageEditorView implements TObserver, DialogPageViewer, ShortcutSupport {

  private final DialogPageModel model;
  private final JPanel panel;
  private final JFrame frame;

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

  private final JLabel canSkipLabel;
  private final JCheckBox canSkipCheck;
  private final Font font = Assets.getInstance().getFont();

  public DialogPageEditorView(DialogPageModel model) {
    this.model = model;
    panel = new JPanel();
    frame = new JFrame();

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

    canSkipLabel = createLabel("Skippable");
    canSkipCheck = new JCheckBox();

    saveButton = new JButton("Save Changes (Shift + Enter)");
    createAnotherButton = new JButton("Make Next Textbox (Ctrl + Enter)");
    panel.setLayout(setupLayout());
    frame.setJMenuBar(createContentMenubar());
    frame.add(panel);
  }

  public static DialogPageEditorView fromModelProceeding(DialogPageModel model) {
    var newView = new DialogPageEditorView(model);
    newView.getContentField().requestFocus();
    return newView;
  }

  private GroupLayout setupLayout() {
    GroupLayout layout = new GroupLayout(panel);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(characterLabel)
                                    .addComponent(contentLabel)
                                    .addComponent(blipLabel)
                                    .addComponent(styleLabel)
                                    .addComponent(canSkipLabel))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(characterField)
                                    .addComponent(contentToolbar)
                                    .addComponent(contentField, GroupLayout.Alignment.CENTER, contentField.getPreferredSize().width, contentField.getPreferredSize().width, contentField.getPreferredSize().width)
                                    .addComponent(blipField)
                                    .addComponent(styleField)
                                    .addComponent(canSkipCheck))
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
                            .addComponent(characterField, GroupLayout.Alignment.CENTER, characterField.getPreferredSize().height, characterField.getPreferredSize().height, characterField.getPreferredSize().height))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(contentToolbar))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(contentLabel)
                            .addComponent(contentField, GroupLayout.Alignment.CENTER, contentField.getPreferredSize().height, contentField.getPreferredSize().height, contentField.getPreferredSize().height))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(blipCheck)
                            .addComponent(blipLabel)
                            .addComponent(blipField, blipField.getPreferredSize().height, blipField.getPreferredSize().height, blipField.getPreferredSize().height))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(styleCheck)
                            .addComponent(styleLabel)
                            .addComponent(styleField, styleField.getPreferredSize().height, styleField.getPreferredSize().height, styleField.getPreferredSize().height))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(canSkipLabel)
                            .addComponent(canSkipCheck))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(saveButton)
                            .addComponent(createAnotherButton)
                    )
    );
    return layout;
  }

  public DialogPageEditorView init() {
    model.attachSubscriber(this);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        unsubscribe(model);
        super.windowClosing(e);
      }
    });
    blipCheck.addActionListener(e -> blipField.setEnabled(blipCheck.isSelected()));
    styleCheck.addActionListener(e -> styleField.setEnabled(styleCheck.isSelected()));
    frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ')');
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.pack();
    update();
    return this;
  }

  public JPanel getPanel() {
    return panel;
  }

  public JFrame getFrame() {
    return frame;
  }

  @Override
  public void update() {
    frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ')');
    blipField.setEnabled(model.getBlipEnabled());
    styleField.setEnabled(model.getStyleEnabled());
    blipCheck.setSelected(model.getBlipEnabled());
    styleCheck.setSelected(model.getStyleEnabled());
    canSkipCheck.setSelected(model.getCanSkip());
    setSpeaker(model.getSpeaker());
    setContent(model.getContent());
    setBlip(model.getBlip());
    setStyle(model.getTextBoxStyle());
  }


  public void attachBlipCheckFunction(ActionListener al) {
    blipCheck.addActionListener(al);
  }

  public void attachStyleCheckFunction(ActionListener al) {
    styleCheck.addActionListener(al);
  }

  @Override
  public String getSpeaker() {
    return characterField.getText();
  }

  @Override
  public void setSpeaker(String newSpeaker) {
    characterField.setText(newSpeaker);
  }

  @Override
  public String getContent() {
    return contentField.getText();
  }

  @Override
  public void setContent(String newContent) {
    contentField.setText(newContent);
  }

  @Override
  public String getBlip() {
    return blipField.getText();
  }

  @Override
  public void setBlip(String newBlip) {
    blipField.setText(newBlip);
  }

  public boolean getBlipEnabled() {
    return blipCheck.isSelected();
  }

  @Override
  public String getStyle() {
    return styleField.getText();
  }

  @Override
  public void setStyle(String newStyle) {
    styleField.setText(newStyle);
  }

  public boolean getStyleEnabled() {
    return styleCheck.isSelected();
  }

  public boolean getCanSkip() {
    return canSkipCheck.isSelected();
  }

  private JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(font);
    field.setForeground(Constants.TextColor.WHITE.toAWT());
    field.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    field.setContentType("text/harlowtml");
    field.setPreferredSize(new Dimension(500, 100));
    return field;
  }

  public JEditorPane getContentField() {
    return contentField;
  }

  public HarlowTMLEditorKit getEditorKit() {
    var kit = contentField.getEditorKit();
    return (HarlowTMLEditorKit) kit;
  }

  protected JToolBar createContentToolbar() {
    var toolbar = new JToolBar();
    var tb = getContentField();
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_RED_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_YELLOW_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_BLUE_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_GREEN_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_GREY_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_WHITE_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.DELAY_ACTION_FIVE));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.DELAY_ACTION_FIFTEEN));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.DELAY_ACTION_SIXTY));
    toolbar.setFloatable(false);
    return toolbar;
  }

  protected JMenuBar createContentMenubar() {
    var menubar = new JMenuBar();
    var tb = getContentField();
    var editMenu = new JMenu("Edit");
    var colorMenu = new JMenu("Colors");
    var behaviorMenu = new JMenu("Behaviors");

    if (tb.getEditorKit() instanceof HarlowTMLEditorKit kit) {
      kit.getColorActions().forEach(colorMenu::add);
      kit.getBehaviorActions().forEach(behaviorMenu::add);
      behaviorMenu.add(kit.getClearBehaviorAction());
      var delay = new JMenuItem(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int magnitude = IntegerDialog.promptForInteger();
          HarlowTMLEditorKit.addTimeDelay(tb, magnitude);
        }
      });
      delay.setText("Delay...");
      editMenu.add(delay);
    }



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
    menubar.add(colorMenu);
    menubar.add(behaviorMenu);
    return menubar;
  }

  protected JTextField createField() {
    var field = new JTextField();
    field.setColumns(40);
    return field;
  }

  protected JLabel createLabel(String text) {
    var label = new JLabel(text);
    label.setHorizontalAlignment(JLabel.LEFT);
    return label;
  }

  @Override
  public void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, Runnable action) {
    var inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = panel.getActionMap();
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    actionMap.put(actionMapKey, actionInstance);
  }

  @Override
  public void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey, AbstractAction action) {
    var inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(keyStroke, actionMapKey);
    var actionMap = panel.getActionMap();
    actionMap.put(actionMapKey, action);
  }

  public void attachSaveAction(Runnable action) {
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    saveButton.addActionListener(actionInstance);
  }

  public void attachContinueAction(Runnable action) {
    var actionInstance = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        action.run();
      }
    };
    createAnotherButton.addActionListener(actionInstance);
    attachKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, true), "Ctrl+Enter released", actionInstance);
  }


}