package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.game.page.TalkPage;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.components.FunctionCallDialog;
import best.tigers.tynkdialog.gui.view.components.IntegerDialog;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.SuperTextDisplayPane;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.util.SwingUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class ChoicePageEditorView extends AbstractPageEditorView {
  private final ChoicePageModel model;
  private final JList<ChoiceResponseModel> responseModelJList;
  private final JSplitPane splitPane = new JSplitPane();
  private final JButton saveButton = new JButton("Save Changes");
  private final JButton addButton = new JButton("add response temp");
  private final JPanel responseEditorView = new JPanel();
  private final SuperTextDisplayPane textDisplayPane = new SuperTextDisplayPane();
  private ChoiceResponseEditorView editorView = null;
  private ChoiceResponseModel selectedModel;
  private final LabeledField character;
  private final JLabel contentLabel;
  private final JEditorPane contentField;
  private final JToolBar contentToolbar;
  private final JButton createAnotherButton;
  private final JCheckBox blipCheck;
  private final LabeledField blip;
  private final JLabel skipWarningLabel;
  private final JPanel skipLabelPanel;
  private final JCheckBox skipCheck;
  private final JPanel responseListSubPanel = new JPanel();
  private final JScrollPane responseListScrollPane = new JScrollPane();

  public ChoicePageEditorView(ChoicePageModel model) {
    this.model = model;
    this.responseModelJList = new JList<>(model.getResponseModels());
    responseListScrollPane.setViewportView(responseModelJList);
    responseListSubPanel.setLayout(new BoxLayout(responseListSubPanel, BoxLayout.Y_AXIS));
    responseListSubPanel.add(responseListScrollPane);
    var addResponseButton = new JButton("add");
    addResponseButton.addActionListener((e)->{
      model.getResponseModels().addResponse(new ChoiceResponseModel(new ChoiceResponse()));
    });
    var deleteResponseButton = new JButton("remove");
    deleteResponseButton.addActionListener((e) -> {
      var selected = responseModelJList.getSelectedValue();
      if (selected != null) {
        if (editorView != null) {
          editorView.unsubscribe(editorView.getModel());
          responseEditorView.removeAll();
          splitPane.repaint();
        }
        model.getResponseModels().deleteResponse(selected);
      }
    });
    var moveUpResponseButton = new JButton("up");
    moveUpResponseButton.addActionListener(e -> {
      var selectedIndex = responseModelJList.getSelectedIndex();
      if (selectedIndex > 0) {
        model.getResponseModels().swapListItems(selectedIndex, selectedIndex - 1);
        responseModelJList.setSelectedIndices(new int[] {selectedIndex - 1});
      } else {
        Toolkit.getDefaultToolkit().beep();
      }
    });
    var moveDownResponseButton = new JButton("down");
    moveDownResponseButton.addActionListener(e -> {
      var selectedIndex = responseModelJList.getSelectedIndex();
      var size = model.getResponseModels().getSize();
      if (selectedIndex < size - 1 && selectedIndex > -1) {
        model.getResponseModels().swapListItems(selectedIndex, selectedIndex + 1);
        responseModelJList.setSelectedIndices(new int[] {selectedIndex + 1});
      } else {
        Toolkit.getDefaultToolkit().beep();
      }
    });
    var responseListSubSubPanel = new JPanel();
    responseListSubSubPanel.setLayout(new BoxLayout(responseListSubSubPanel, BoxLayout.X_AXIS));
    responseListSubSubPanel.add(addResponseButton);
    responseListSubSubPanel.add(deleteResponseButton);
    responseListSubSubPanel.add(moveUpResponseButton);
    responseListSubSubPanel.add(moveDownResponseButton);
    responseListSubPanel.add(responseListSubSubPanel);
    splitPane.setLeftComponent(responseListSubPanel);
    splitPane.setRightComponent(responseEditorView);
    splitPane.setBorder(new EtchedBorder());
    getFrame().setPreferredSize(new Dimension(1000, 700));
    getFrame().pack();
    addButton.addActionListener((e) -> {
      var x = new ChoiceResponseModel(new ChoiceResponse());
      x.setContent("example");
      model.getResponseModels().addResponse(x);
    });
    responseModelJList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        setEditorPaneModel(responseModelJList.getSelectedValue());
      }
    });
    responseModelJList.setCellRenderer(new ChoiceRenderer());
    character = new LabeledField("Character");

    contentLabel = new JLabel("Content");
    contentField = createContentField();
    contentToolbar = createContentToolbar();
    blip = new LabeledField("Blip");
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
                                    .addComponent(blip.getField())
                                    .addComponent(skipLabelPanel, GroupLayout.Alignment.LEADING))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(blipCheck)))
                    .addComponent(splitPane)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(saveButton)));
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
                            .addComponent(skipCheck)
                            .addComponent(skipLabelPanel))
                    .addComponent(splitPane)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(saveButton)
                    )
    );
    return layout;
  }


  public void setEditorPaneModel(ChoiceResponseModel responseModel) {
    responseEditorView.removeAll();
    if (editorView != null) {
      editorView.unsubscribe(editorView.getModel());
    }
    if (responseModel != null) {
      editorView = new ChoiceResponseEditorView(responseModel, model.getResponseModels());
      responseEditorView.add(editorView);
      splitPane.repaint();
      splitPane.resetToPreferredSizes();
      splitPane.repaint();
    }
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
  }

  @Override
  public AbstractPage asPage() {
    var blipValue = blip.getText();
    if (!blipCheck.isSelected()) {
      blipValue = null;
    }
    return new ChoicePage(character.getText(), contentField.getText(), blipValue,
            skipCheck.isSelected(), new ArrayList<>(), model.getResponses());
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
  public void init() {
    super.init();
    blipCheck.addActionListener(e -> blip.setEnabled(blipCheck.isSelected()));
  }

  @Override
  AbstractPageModel getModel() {
    return model;
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
  }
}

