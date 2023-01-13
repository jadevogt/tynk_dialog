package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.gui.view.ShortcutSupport;
import best.tigers.tynkdialog.gui.view.TObserver;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

public class TalkPageEditorView implements TObserver, TalkPageViewer, ShortcutSupport {

  private final TalkPageModel model;
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
  private final Font font = Assets.getInstance().getFont();

  public TalkPageEditorView(TalkPageModel model) {
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

    saveButton = new JButton("Save Changes (Shift + Enter)");
    createAnotherButton = new JButton("Make Next Textbox (Ctrl + Enter)");
    panel.setLayout(setupLayout());
    frame.setJMenuBar(createContentMenubar());
    frame.add(panel);
  }

  public static TalkPageEditorView fromModelProceeding(TalkPageModel model) {
    var newView = new TalkPageEditorView(model);
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
                    .addComponent(styleLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(characterField)
                    .addComponent(contentToolbar)
                    .addComponent(contentField, GroupLayout.Alignment.CENTER,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width)
                    .addComponent(blipField)
                    .addComponent(styleField))
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
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(saveButton)
                .addComponent(createAnotherButton)
            )
    );
    return layout;
  }

  public TalkPageEditorView init() {
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
    frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
    blipField.setEnabled(model.getBlipEnabled());
    styleField.setEnabled(model.getStyleEnabled());
    blipCheck.setSelected(model.getBlipEnabled());
    styleCheck.setSelected(model.getStyleEnabled());
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

  private JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(font);
    field.setForeground(Constants.TextColor.WHITE.toAWT());
    field.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    field.setContentType("supertext/supertext");
    field.setPreferredSize(new Dimension(500, 100));
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
    var toolbar = new JToolBar();
    var tb = getContentField();
    var function = new JButton(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] details = FunctionCallDialog.promptForFunctionDetails();
        SuperTextEditorKit.addFunctionCall(tb, details[0], details[1]);
      }
    });
    function.setText("Function call...");
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_RED_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_YELLOW_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_BLUE_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_GREEN_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_GREY_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.TYNK_WHITE_TEXT));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.DELAY_ACTION_FIVE));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.DELAY_ACTION_FIFTEEN));
    toolbar.add(tb.getActionMap().get(SuperTextEditorKit.DELAY_ACTION_SIXTY));
    toolbar.add(function);
    toolbar.setFloatable(false);
    return toolbar;
  }

  protected JMenuBar createContentMenubar() {
    var menubar = new JMenuBar();
    var tb = getContentField();
    var editMenu = new JMenu("Edit");
    var insertMenu = new JMenu("Insert");
    var colorMenu = new JMenu("Colors");
    var behaviorMenu = new JMenu("Behaviors");

    if (tb.getEditorKit() instanceof SuperTextEditorKit kit) {
      kit.getColorActions().forEach(colorMenu::add);
      kit.getBehaviorActions().forEach(behaviorMenu::add);
      behaviorMenu.add(kit.getClearBehaviorAction());
      var delay = new JMenuItem(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int magnitude = IntegerDialog.promptForInteger();
          SuperTextEditorKit.addTimeDelay(tb, magnitude);
        }
      });
      delay.setText("Delay...");
      var function = new JMenuItem(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String[] details = FunctionCallDialog.promptForFunctionDetails();
          SuperTextEditorKit.addFunctionCall(tb, details[0], details[1]);
        }
      });
      function.setText("Function call...");
      insertMenu.add(delay);
      insertMenu.add(function);
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
    menubar.add(insertMenu);
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
  public void attachFunctionalKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      Runnable action) {
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
  public void attachKeyboardShortcut(KeyStroke keyStroke, String actionMapKey,
      AbstractAction action) {
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
    attachKeyboardShortcut(
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK, true),
        "Ctrl+Enter released", actionInstance);
  }
}