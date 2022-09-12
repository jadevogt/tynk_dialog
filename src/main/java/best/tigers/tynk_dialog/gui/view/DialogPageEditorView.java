package best.tigers.tynk_dialog.gui.view;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

import best.tigers.tynk_dialog.game.Constants;
import best.tigers.tynk_dialog.gui.Assets;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;
import best.tigers.tynk_dialog.gui.text.HarlowTMLEditorKit;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

public class DialogPageEditorView implements TObserver, DialogPageViewer {

  private final DialogPageModel model;
  private final JPanel panel;
  private final JFrame frame;

  private final JLabel characterLabel;
  private final JTextField characterField;

  private final JLabel contentLabel;
  private final JEditorPane contentField;
  private final JToolBar contentToolbar;

  private final JButton saveButton;

  private final JCheckBox blipCheck;

  private final JLabel blipLabel;
  private final JTextField blipField;

  private final JCheckBox styleCheck;

  private final JLabel styleLabel;
  private final JTextField styleField;
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

    saveButton = new JButton("Save Changes (Shift + Enter)");
    saveButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING)));
    panel.setLayout(setupLayout());
    frame.setJMenuBar(createContentMenubar());
    frame.add(panel);
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
                                    .addComponent(contentField, GroupLayout.Alignment.CENTER, contentField.getPreferredSize().width, contentField.getPreferredSize().width, contentField.getPreferredSize().width)
                                    .addComponent(blipField)
                                    .addComponent(styleField))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(blipCheck)
                                    .addComponent(styleCheck)))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(saveButton)));
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
                    .addComponent(saveButton)
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


  public void attachSaveFunction(ActionListener al) {
    saveButton.addActionListener(al);
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

  protected JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(font);
    field.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    field.setContentType("text/harlowtml");
    field.setPreferredSize(new Dimension(500, 100));
    return field;
  }

  protected JEditorPane getContentField() {
    return contentField;
  }

  protected JToolBar createContentToolbar() {
    var toolbar = new JToolBar();
    var tb = getContentField();
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_RED_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_YELLOW_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_BLUE_TEXT));
    toolbar.add(tb.getActionMap().get(HarlowTMLEditorKit.TYNK_GREEN_TEXT));
    toolbar.add(tb.getActionMap().get("Delay5"));
    toolbar.add(tb.getActionMap().get("Delay15"));
    toolbar.add(tb.getActionMap().get("Delay60"));
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
          int magnitude = CommonDialogs.IntegerDialog.promptForInteger();
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

}