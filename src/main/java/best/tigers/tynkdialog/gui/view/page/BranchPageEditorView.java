package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.ChoiceResponse;
import best.tigers.tynkdialog.gui.controller.GenericListController;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.model.page.ChoiceResponseModel;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.ScrollingListEditor;
import best.tigers.tynkdialog.util.Log;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import lombok.Getter;
import lombok.Setter;

public class BranchPageEditorView extends AbstractPageEditorView implements ListDataListener {

  private BranchPageModel model;
  private final ScrollingListEditor<BranchRequirementModel> branchListEditor;
  private GenericListModel<BranchRequirementModel> branchListModel;
  private JFrame frame = getFrame();
  private JPanel panel = getPanel();
  private LabeledField result = new LabeledField("result");
  private JComboBox<Leaf> leafComboBox = new JComboBox<>();
  private JButton saveButton = new JButton("save changes");
  private GenericListController<BranchRequirementModel> branchListController;
  private final JPanel requirementEditorPanel = new JPanel();
  @Getter @Setter
  private BranchRequirementEditorView currentEditorView = null;
  public BranchPageEditorView(AbstractPageModel pageModel) {
    model = (BranchPageModel) pageModel;

    branchListModel = model.cloneRequirements();
    branchListModel.addListDataListener(this);
    branchListEditor = new ScrollingListEditor<>(branchListModel);
    branchListEditor.setList(branchListModel);

    var layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
    panel.setLayout(layout);
    leafComboBox.addItem(Leaf.ADD);
    leafComboBox.addItem(Leaf.JUMP);
    frame.add(panel);
    var resultSubPanel = new JPanel();
    panel.add(leafComboBox);
    resultSubPanel.add(result.getLabel());
    resultSubPanel.add(result.getField());
    panel.add(resultSubPanel);
    panel.add(branchListEditor);
    panel.add(requirementEditorPanel);
    panel.add(saveButton);
    frame.pack();

    frame.setVisible(true);
  }

  @Override
  public BranchPage asPage() {
    branchListModel.getContent().stream().map(b -> b.clone().getRequirement()).forEach(m -> Log.info(m.getValue()));
    var listModel = branchListModel;
    return new BranchPage((Leaf) leafComboBox.getSelectedItem(), result.getText(),
        listModel.getContent().stream().map(BranchRequirementModel::getRequirement).toList());
  }

  @Override
  AbstractPageModel getModel() {
    return model;
  }

  public void setEditorPaneModel(BranchRequirementModel requirementModel) {
    var requirementPanel = requirementEditorPanel;
    var editorView = currentEditorView;
    requirementPanel.removeAll();
    if (editorView != null) {
      editorView.unsubscribe(editorView.getModel());
    }
    if (requirementModel != null) {
      setCurrentEditorView(new BranchRequirementEditorView(requirementModel, branchListModel));
      requirementPanel.add(currentEditorView);
      requirementPanel.repaint();
      frame.pack();
    }
  }

  @Override
  public void update() {
    super.update();
    leafComboBox.setSelectedItem(model.getLeaf());
    result.setText(model.getBranchResult());

    branchListModel.removeListDataListener(this);
    branchListModel = model.cloneRequirements();
    branchListModel.addListDataListener(this);
    branchListEditor.setList(branchListModel);


    branchListController = new GenericListController<>(branchListModel);
    branchListEditor.setAddAction(() -> branchListController.addChoice(new BranchRequirementModel(new BranchRequirement())));
    branchListEditor.setDeleteItemAction((indexConsumer) -> branchListController.deleteChoice(indexConsumer, () -> {}));
    branchListEditor.setMoveUpAction(branchListController::moveUp);
    branchListEditor.setMoveDownAction(branchListController::moveDown);
    branchListEditor.setListSelectionAction(this::setEditorPaneModel);
  }

  @Override
  public void setupSaveActions() {

    saveButton.addActionListener(getSaveAction());

  }

  @Override
  public void intervalAdded(ListDataEvent e) {
    branchListEditor.getList().revalidate();
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    branchListEditor.getList().revalidate();
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    branchListEditor.getList().revalidate();
  }
}
