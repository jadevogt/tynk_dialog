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

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
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

public class NeoTalkPageEditorView extends AbstractPageEditorView {

  private JPanel rootPanel;
  private javax.swing.JComboBox<String> character;
  private JCheckBox blipCheck;
  private TalkPageModel model;
  private javax.swing.JComboBox<String> blip;
  private javax.swing.JComboBox<String> style;
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

  public NeoTalkPageEditorView(TalkPageModel model){
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
  }
}
