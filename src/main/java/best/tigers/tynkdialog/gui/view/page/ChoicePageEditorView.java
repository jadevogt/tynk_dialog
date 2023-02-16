package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.ChoicePage;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.controller.GenericListController;
import best.tigers.tynkdialog.gui.model.AbstractModel;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.ChoicePageModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.ScrollingListEditor;
import best.tigers.tynkdialog.gui.view.components.SuperTextContentMenuBar;
import best.tigers.tynkdialog.gui.view.components.SuperTextEditorPane;
import best.tigers.tynkdialog.gui.view.components.SuperTextToolbar;
import best.tigers.tynkdialog.util.Log;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import lombok.Getter;
import lombok.Setter;

public class ChoicePageEditorView extends AbstractPageEditorView implements ListDataListener {

  private final ChoicePageModel model;
  @Getter
  private final ScrollingListEditor<ChoiceResponseModel> responseListEditor;
  @Getter
  private final ScrollingListEditor<String> giftListEditor;
  private final JSplitPane splitPane = new JSplitPane();
  private final JButton saveButton = new JButton("Save Changes");
  @Getter
  private final JPanel responseEditorPanel = new JPanel();
  private final LabeledField character = new LabeledField("Character");
  private final JLabel contentLabel = new JLabel("Content");
  private final SuperTextEditorPane contentField = new SuperTextEditorPane(1);
  private final JCheckBox blipCheck;
  private final LabeledField blip = new LabeledField("Blip");
  private final JLabel skipWarningLabel = new JLabel("");
  private final JPanel skipLabelPanel = new JPanel();
  private final JCheckBox skipCheck = new JCheckBox();
  private final JToolBar contentToolbar = new SuperTextToolbar(contentField, this::autoDisableSkip);
  private GenericListController<ChoiceResponseModel> responseListController = null;
  private GenericListController<String> giftListController = null;
  @Getter
  private GenericListModel<ChoiceResponseModel> responseListModel;
  @Getter
  private GenericListModel<String> giftListModel;
  @Getter
  @Setter
  private ChoiceResponseEditorView responseEditorView = null;

  public ChoicePageEditorView(ChoicePageModel model) {
    this.model = model;

    //this.responseListModel = model.getResponseListModel();
    //this.giftListModel = model.getGiftListModel();
    responseListModel = model.cloneChoiceResponses();
    responseListModel.addListDataListener(this);
    giftListModel = model.cloneGifts();
    giftListModel.addListDataListener(this);

    this.responseListEditor = new ScrollingListEditor<>(responseListModel, new ChoiceRenderer());
    this.giftListEditor = new ScrollingListEditor<>(giftListModel);

    splitPane.setLeftComponent(responseListEditor);
    splitPane.setRightComponent(responseEditorPanel);
    splitPane.setBorder(new EtchedBorder());
    getFrame().setPreferredSize(new Dimension(1000, 700));
    getFrame().pack();

    blipCheck = new JCheckBox();

    JLabel skipLabel = createLabel("Skippable");
    skipLabelPanel.setLayout(new BoxLayout(skipLabelPanel, BoxLayout.X_AXIS));
    skipLabelPanel.add(skipLabel, Component.LEFT_ALIGNMENT);
    skipLabelPanel.add(skipWarningLabel, Component.LEFT_ALIGNMENT);
    skipWarningLabel.setFont(skipWarningLabel.getFont().deriveFont(Font.ITALIC));
    skipWarningLabel.setForeground(Color.decode("#888888"));

    getPanel().setLayout(setupLayout());
    getFrame().setJMenuBar(new SuperTextContentMenuBar(this::autoDisableSkip));
  }

  public void clearEditorView() {
    if (responseEditorView != null) {
      responseEditorView.unsubscribe(responseEditorView.getModel());
      responseEditorPanel.removeAll();
      splitPane.repaint();
    }
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
            .addComponent(giftListEditor)
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
            .addComponent(giftListEditor)
            .addComponent(splitPane)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(saveButton)
            )
    );
    return layout;
  }

  @Override
  public void subscribe(AbstractModel model) {
    super.subscribe(model);
  }

  public void repaintSplitPane() {
    splitPane.repaint();
    splitPane.resetToPreferredSizes();
    splitPane.repaint();
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

    responseListModel.removeListDataListener(this);
    responseListModel = model.cloneChoiceResponses();
    responseListModel.addListDataListener(this);
    responseListEditor.setList(responseListModel);

    giftListModel.removeListDataListener(this);
    giftListModel = model.cloneGifts();
    giftListModel.addListDataListener(this);
    giftListEditor.setList(giftListModel);

    responseListController = new GenericListController<>(responseListModel);
    var responseEditor = responseListEditor;
    responseEditor.setAddAction(
        () -> responseListController.addChoice(new ChoiceResponseModel(new ChoiceResponse())));
    responseEditor.setDeleteItemAction((indexConsumer) -> {
      responseListController.deleteChoice(indexConsumer, this::clearEditorView);
      Log.info(responseListController.toString());
    });
    responseEditor.setMoveUpAction(responseListController::moveUp);
    responseEditor.setMoveDownAction(responseListController::moveDown);
    responseEditor.setListSelectionAction(this::setEditorPaneModel);

    giftListController = new GenericListController<>(giftListModel);
    var giftEditor = giftListEditor;
    giftEditor.setAddAction(
        () -> giftListController.addChoice(JOptionPane.showInputDialog("Enter gift:")));
    giftEditor.setDeleteItemAction(
        (indexConsumer) -> giftListController.deleteChoice(indexConsumer, () -> {
        }));
    giftEditor.setMoveUpAction(giftListController::moveUp);
    giftEditor.setMoveDownAction(giftListController::moveDown);
  }

  public void setEditorPaneModel(ChoiceResponseModel responseModel) {
    var panel = responseEditorPanel;
    var editorView = responseEditorView;
    panel.removeAll();
    if (editorView != null) {
      editorView.unsubscribe(editorView.getModel());
    }
    if (responseModel != null) {
      setResponseEditorView(new ChoiceResponseEditorView(responseModel, responseListModel));
      panel.add(responseEditorView);
      repaintSplitPane();
    }
  }

  @Override
  public AbstractPage asPage() {
    var blipValue = blipCheck.isSelected() ? blip.getText() : null;
    var responseModels = responseListModel.getContent()
        .stream()
        .map(ChoiceResponseModel::getChoiceResponse)
        .toList();
    return new ChoicePage(
        character.getText(),
        contentField.getText(),
        blipValue,
        skipCheck.isSelected(),
        giftListModel.getContent(),
        responseModels);

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
  public AbstractPageModel getModel() {
    return model;
  }

  @Override
  public void setupSaveActions() {
    saveButton.addActionListener(getSaveAction());
  }

  @Override
  public void intervalAdded(ListDataEvent e) {
    if (responseEditorView != null) {
      responseEditorView.repaint();
    }
    giftListEditor.repaint();
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    if (responseEditorView != null) {
      responseEditorView.repaint();
    }
    giftListEditor.repaint();
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    if (responseEditorView != null) {
      responseEditorView.repaint();
    }
    giftListEditor.repaint();
  }
}

