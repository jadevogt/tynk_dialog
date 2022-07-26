package best.tigers.tynk_dialog.gui.view;

import best.tigers.tynk_dialog.gui.Integration;
import best.tigers.tynk_dialog.gui.model.DialogPageModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public class DialogPageEditorView implements Observer, DialogPageViewer {
  final private static Dimension PREFERRED_SIZE = new Dimension(300, 400);

  private final DialogPageModel model;
  private final JPanel panel;
  private final JFrame frame;
  private final JTextField characterField;
  private final JTextArea contentField;
  private final JButton saveButton;
  private final JCheckBox blipCheck;
  private final JTextArea blipField;
  private final JCheckBox styleCheck;
  private final JTextArea styleField;

  public DialogPageEditorView(DialogPageModel model) {
    Integration.runIntegrations();
    this.model = model;
    panel = new JPanel();
    frame = new JFrame();

    JLabel characterLabel = new JLabel("Character");
    characterLabel.setHorizontalAlignment(JLabel.LEFT);
    characterField = new JTextField();
    characterField.setColumns(20);

    JScrollPane scroller = new JScrollPane();
    JLabel contentLabel = new JLabel("Content");
    contentLabel.setHorizontalAlignment(JLabel.LEFT);
    contentField = new JTextArea();
    contentField.setColumns(20);
    contentField.setLineWrap(true);
    contentField.setRows(4);

    JLabel blipLabel = new JLabel("Blip");
    blipLabel.setHorizontalAlignment(JLabel.LEFT);
    blipField = new JTextArea();
    blipField.setColumns(20);
    blipCheck = new JCheckBox();

    JLabel styleLabel = new JLabel("Textbox");
    styleLabel.setHorizontalAlignment(JLabel.LEFT);
    styleField = new JTextArea();
    styleField.setColumns(20);
    styleCheck = new JCheckBox();

    saveButton = new JButton("Save Changes");

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
                    .addComponent(contentField, GroupLayout.Alignment.CENTER, 0, contentField.getPreferredSize().width, Short.MAX_VALUE)
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
                .addComponent(contentLabel)
                .addComponent(contentField, GroupLayout.Alignment.CENTER, 100, 100, Short.MAX_VALUE))
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
    panel.setLayout(layout);
    frame.add(panel);
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
    blipCheck.addActionListener(e -> {
      blipField.setEnabled(blipCheck.isSelected());
    });
    styleCheck.addActionListener(e -> {
      styleField.setEnabled(styleCheck.isSelected());
    });
    panel.setPreferredSize(PREFERRED_SIZE);
    frame.setPreferredSize(PREFERRED_SIZE);
    frame.setTitle("DialogPage Editor (" + model.getSpeaker() + ")");
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    update();
    return this;
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
  public void setBlip(String newBlip) {
    blipField.setText(newBlip);
  }

  @Override
  public String getBlip() {
    return blipField.getText();
  }

  public boolean getBlipEnabled() {
    return blipCheck.isSelected();
  }

  @Override
  public void setStyle(String newStyle) {
    styleField.setText(newStyle);
  }

  @Override
  public String getStyle() {
    return styleField.getText();
  }

  public boolean getStyleEnabled() {
    return styleCheck.isSelected();
  }

  public void makeTrans() {
    saveButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WINDOW_CLOSING)));
  }
}