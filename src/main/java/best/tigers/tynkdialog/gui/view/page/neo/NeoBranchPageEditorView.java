package best.tigers.tynkdialog.gui.view.page.neo;

import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.gui.controller.GenericListController;
import best.tigers.tynkdialog.gui.model.GenericListModel;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchPageModel;
import best.tigers.tynkdialog.gui.model.page.BranchRequirementModel;
import best.tigers.tynkdialog.gui.view.components.neo.NeoScrollingListEditor;
import best.tigers.tynkdialog.gui.view.page.AbstractPageEditorView;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class NeoBranchPageEditorView extends AbstractPageEditorView implements ListDataListener {
    private final BranchPageModel model;
    private JTextField result;
    private JButton saveButton;
    private JPanel rootPane;
    private JComboBox<BranchPage.Leaf> leafComboBox;

    private GenericListController<BranchRequirementModel> branchListController;
    private GenericListModel<BranchRequirementModel> requirementListModel;
    private JPanel requirementEditorPanel;
    private NeoScrollingListEditor<BranchRequirementModel> requirementListEditor;
    @Getter
    @Setter
    private NeoBranchRequirementEditorView currentEditorView = null;

    public NeoBranchPageEditorView(AbstractPageModel pageModel) {
        model = (BranchPageModel) pageModel;
        var panel = rootPane;
        var frame = getFrame();
        getFrame().add(panel);
        leafComboBox.addItem(BranchPage.Leaf.ADD);
        leafComboBox.addItem(BranchPage.Leaf.JUMP);
        var requirementModels = model.getPage()
                .getRequirements()
                .stream()
                .map(BranchRequirementModel::new)
                .toList();

        requirementListModel = new GenericListModel<>(requirementModels);
        requirementListEditor.setList(requirementListModel);
        requirementListModel.addListDataListener(this);

        frame.pack();
        frame.setVisible(true);
        setEditorPaneModel(null);
        frame.pack();
        frame.setTitle("BranchPage Editor");
    }


    @Override
    public BranchPage asPage() {
        var listModel = requirementListModel;
        var newLeaf = (BranchPage.Leaf) leafComboBox.getSelectedItem();
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
        var newRequirementModel = requirementListEditor.getList().getSelectedValue();
        System.out.println(newRequirementModel);
        var requirementPanel = requirementEditorPanel;
        var editorView = currentEditorView;
        requirementPanel.removeAll();
        if (editorView != null) {
            editorView.unsubscribe(editorView.getModel());
        }
        if (newRequirementModel != null) {
            var newView = new NeoBranchRequirementEditorView(newRequirementModel, requirementListModel);
            setCurrentEditorView(newView);
            requirementPanel.add(newView.getRootPanel());
            requirementPanel.repaint();
            getFrame().pack();
        }
        else {
            var newView = NeoBranchRequirementEditorView.nullView();
            setCurrentEditorView(newView);
            requirementPanel.add(newView.getRootPanel());
            requirementPanel.repaint();
            getFrame().pack();
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

    private void createUIComponents() {
        requirementListEditor = new NeoScrollingListEditor<>();
        requirementEditorPanel = new JPanel();
    }
}
