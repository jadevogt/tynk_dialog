package best.tigers.tynkdialog.gui.view.page;

import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.gui.controller.GenericListController;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.view.components.LabeledField;
import best.tigers.tynkdialog.gui.view.components.ScrollingListEditor;
import best.tigers.tynkdialog.gui.view.page.neo.NeoBranchRequirementEditorView;
import best.tigers.tynkdialog.util.Log;
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

  private final BranchPageModel model;
  private final ScrollingListEditor<BranchRequirementModel> requirementListEditor;
  private GenericListModel<BranchRequirementModel> requirementListModel;
  private final JFrame frame = getFrame();
  private final JPanel panel = getPanel();
  private final LabeledField result = new LabeledField("result");
  private final JComboBox<Leaf> leafComboBox = new JComboBox<>();
  private final JButton saveButton = new JButton("save changes");
  private GenericListController<BranchRequirementModel> branchListController;
  private final JPanel requirementEditorPanel = new JPanel();
  @Getter @Setter
  private NeoBranchRequirementEditorView currentEditorView = null;
  public BranchPageEditorView(AbstractPageModel pageModel) {
    model = (BranchPageModel) pageModel;

    var requirementModels = model.getPage()
        .getRequirements()
        .stream()
        .map(BranchRequirementModel::new)
        .toList();

    requirementListModel = new GenericListModel<>(requirementModels);
    requirementListModel.addListDataListener(this);

    requirementListEditor = new ScrollingListEditor<>(requirementListModel);
    requirementListEditor.setList(requirementListModel);

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
    panel.add(requirementListEditor);
    panel.add(requirementEditorPanel);
    panel.add(saveButton);
    frame.pack();

    frame.setVisible(true);
  }

  @Override
  public BranchPage asPage() {
    var listModel = requirementListModel;
    var newLeaf = (Leaf) leafComboBox.getSelectedItem();
    return new BranchPage(
        newLeaf,
        result.getText(),
        listModel.getContent().stream().map(BranchRequirementModel::getRequirement).toList()
    );
  }

  @Override
  public AbstractPageModel getModel() {
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
      setCurrentEditorView(new NeoBranchRequirementEditorView(requirementModel, requirementListModel));
      requirementPanel.add(currentEditorView.getRootPanel());
      requirementPanel.repaint();
      frame.pack();
    }
  }

  @Override
  public void update() {
    super.update();
    leafComboBox.setSelectedItem(model.getLeaf());
    result.setText(model.getBranchResult());

    var requirementModels = model.getPage()
        .getRequirements()
        .stream()
        .map(BranchRequirementModel::new)
        .toList();

    requirementListModel.removeListDataListener(this);
    requirementListModel = new GenericListModel<>(requirementModels);
    requirementListModel.addListDataListener(this);
    requirementListEditor.setList(requirementListModel);

    branchListController = new GenericListController<>(requirementListModel);
    requirementListEditor.setAddAction(() -> branchListController.addChoice(new BranchRequirementModel(new BranchRequirement())));
    requirementListEditor.setDeleteItemAction((indexConsumer) -> branchListController.deleteChoice(indexConsumer, () -> {}));
    requirementListEditor.setMoveUpAction(branchListController::moveUp);
    requirementListEditor.setMoveDownAction(branchListController::moveDown);
    requirementListEditor.setListSelectionAction(this::setEditorPaneModel);
  }

  @Override
  public void setupSaveActions() {

    saveButton.addActionListener(getSaveAction());

  }

  @Override
  public void intervalAdded(ListDataEvent e) {
    requirementListEditor.getList().revalidate();
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {
    requirementListEditor.getList().revalidate();
  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    requirementListEditor.getList().revalidate();
  }
}
