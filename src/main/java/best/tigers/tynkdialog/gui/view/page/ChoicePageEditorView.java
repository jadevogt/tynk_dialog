package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.model.ResponseChoiceListModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.view.components.ChoiceResponseDialog;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import best.tigers.tynkdialog.gui.view.components.SuperTextDisplayPane;
import best.tigers.tynkdialog.supertext.SuperTextDocument;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.text.DefaultEditorKit;

public class ChoicePageEditorView extends AbstractPageEditorView {

  private final ChoicePageModel model;
  private final LabeledField character;
  private final JLabel contentLabel;
  private final JEditorPane contentField;
  private final JToolBar contentToolbar;
  private final JButton saveButton;
  private final JButton createAnotherButton;
  private final JCheckBox blipCheck;
  private final LabeledField blip;
  private final JLabel skipWarningLabel;
  private final JPanel skipLabelPanel;
  private final JCheckBox skipCheck;

  private final JList<ChoiceResponse> responseList;
  private final JScrollPane scrollPane;



  public ChoicePageEditorView(ChoicePageModel model) {
    super();
    this.model = model;
    character = new LabeledField("Character");

    contentLabel = new JLabel("Content");
    contentField = createContentField();
    contentToolbar = createContentToolbar();
    blip = new LabeledField("Blip");
    responseList = new JList<>();
    responseList.setModel(new ResponseChoiceListModel(model.getResponses()));
    responseList.setCellRenderer(new ResponseCellRenderer());
    scrollPane = new JScrollPane();
    scrollPane.setViewportView(responseList);
    blipCheck = new JCheckBox();
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
                    .addComponent(skipCheck))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(character.getField())
                    .addComponent(contentToolbar)
                    .addComponent(contentField, GroupLayout.Alignment.CENTER,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width)
                    .addComponent(scrollPane, Alignment.CENTER,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width,
                        contentField.getPreferredSize().width)
                    .addComponent(blip.getField())
                    .addComponent(skipLabelPanel, Alignment.LEADING))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(blipCheck)))
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
            .addComponent(scrollPane)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(blipCheck)
                .addComponent(blip.getLabel())
                .addComponent(blip.getField(), blip.getHeight(), blip.getHeight(),
                    blip.getHeight()))
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
  }

  @Override
  ChoicePageModel getModel() {
    return model;
  }

  @Override
  public void update() {
    super.update();
    getFrame().setTitle("ChoicePage Editor (" + model.getSpeaker() + ")");
    var isBlipEnabled = model.getBlip() != null;
    blip.setEnabled(isBlipEnabled);
    blipCheck.setSelected(isBlipEnabled);
    character.setText(model.getSpeaker());
    contentField.setText(model.getContent());
    blip.setText(model.getBlip());
    skipCheck.setSelected(model.isCanSkip());
    responseList.setModel(new ResponseChoiceListModel(model.getResponses()));
  }

  public ChoicePage asPage() {
    var blipValue = blip.getText();
    if (!blipCheck.isSelected()) {
      blipValue = null;
    }
    return new ChoicePage(character.getText(), contentField.getText(), blipValue,
        skipCheck.isSelected(), new ArrayList<String>(), ((ResponseChoiceListModel) responseList.getModel()).getContent());
  }

  private JEditorPane createContentField() {
    var field = new JEditorPane();
    field.setMargin(new Insets(0, 0, 0, 0));
    field.setFont(Assets.getTerminus());
    field.setForeground(Constants.TextColor.WHITE.toAWT());
    field.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    field.setContentType("supertext/supertext");
    field.setPreferredSize(new Dimension(600, 25));
    if (field.getDocument() instanceof SuperTextDocument superTextDocument) {
      superTextDocument.setMaxLines(1);
    }
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
    skipCheck.setSelected(false);
    skipWarningLabel.setText(" (auto-unchecked due to function call)");
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
    createAnotherButton.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ((ResponseChoiceListModel) responseList.getModel()).addResponse(ChoiceResponseDialog.promptForResponseDetails());
      }
    });
  }

  class ResponseCellRenderer implements ListCellRenderer<ChoiceResponse> {
    @Override
    public Component getListCellRendererComponent(JList<? extends ChoiceResponse> list,
        ChoiceResponse value, int index, boolean isSelected, boolean cellHasFocus) {
      var component = new JPanel();
      component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
      var subPanel = new JPanel();
      subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
      var textPane = new SuperTextDisplayPane(1);
      textPane.setText(value.getContent());
      textPane.setFont(Assets.getLittle());
      textPane.setLightestBackground();
      component.add(textPane);
      subPanel.add(new QuickPair("Result", value.getChoiceResult()), Component.LEFT_ALIGNMENT);
      subPanel.add(new QuickPair("Icon", value.getIcon().name()), Component.RIGHT_ALIGNMENT);
      subPanel.setBackground(null);
      component.add(subPanel, Component.LEFT_ALIGNMENT);
      if (isSelected) {
        textPane.setLighterBackground();
        component.setBackground(Assets.getDefaults().getColor("List.selectionBackground"));
        component.setForeground(Assets.getDefaults().getColor("List.selectionForeground"));
      }
      return component;
    }
  }
}